package com.github.offile.permissionshelper.runtime

import android.content.pm.PackageManager
import com.github.offile.permissionshelper.core.Request
import com.github.offile.permissionshelper.core.Source
import java.util.*
import kotlin.collections.ArrayList
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * Implementation of request runtime permission
 */
class RuntimeRequest(
    source: Source,
    private val permissions: LinkedHashSet<String>,
    private var onShowRationale: RuntimeShowRationaleFun? = null,
    private var onNeverAskAgain: NeverAskAgainFun? = null,
) : Request<RuntimeResult>(source) {

    /**
     * check permissions
     */
    @OptIn(ExperimentalContracts::class)
    private inline fun checkPermissions(
        permissions: Set<String>,
        callback: CheckPermissionsCallback,
    ) {
        contract {
            callsInPlace(callback, InvocationKind.EXACTLY_ONCE)
        }
        val grantedList = ArrayList<String>()
        val noGrantedList = ArrayList<String>()
        permissions.forEach {
            if (source.checkPermission(it)) {
                grantedList.add(it)
            } else {
                noGrantedList.add(it)
            }
        }
        val isGranted = grantedList.size == permissions.size
        callback(isGranted, grantedList, noGrantedList)
    }

    override fun request(callback: RuntimePermissionsResultCallback) {
        checkPermissions(permissions) { isGranted, grantedList, noGrantedList ->
            if (isGranted) {
                // All permissions are granted
                callback.onResult(true, grantedList, emptyList())
            } else {
                // there are still permissions not granted
                requestAndCheckRationale(grantedList, noGrantedList, callback)
            }
        }
    }

    /**
     * Check if you want to explain before requesting permission
     */
    private fun requestAndCheckRationale(
        grantedList: MutableList<String>,
        noGrantedList: MutableList<String>,
        callback: RuntimePermissionsResultCallback
    ) {
        // create request
        val requestFun = {
            requestNoGrantedPermissions(
                grantedPermissions = grantedList,
                noGrantedPermissions = noGrantedList,
                callback = callback
            )
        }
        if (onShowRationale != null) {
            val rationaleList =
                noGrantedList.filter(source::shouldShowRequestPermissionRationale)
            if (rationaleList.isNotEmpty()) {
                val showRationaleScope = object : RuntimeShowRationaleScope {
                    override val permissions: List<String> get() = rationaleList

                    override fun proceed() = requestFun()

                    override fun cancel() = callback.onResult(
                        isGranted = false,
                        grantedPermissions = grantedList,
                        deniedPermissions = noGrantedList,
                    )
                }
                onShowRationale!!.onShowRationale(showRationaleScope)
            } else {
                requestFun()
            }
        } else {
            requestFun()
        }
    }

    /**
     * Only request permissions that have not been granted
     */
    private fun requestNoGrantedPermissions(
        grantedPermissions: MutableList<String>,
        noGrantedPermissions: MutableList<String>,
        callback: RuntimePermissionsResultCallback,
    ) {
        source.requestPermissions(
            *noGrantedPermissions.toTypedArray()
        ) { p, grantResults ->
            val deniedList = LinkedList(noGrantedPermissions)
            p.forEachIndexed { index, s ->
                if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                    grantedPermissions.add(s)
                    deniedList.remove(s)
                }
            }
            val isGranted = deniedList.isEmpty()
            if (isGranted) {
                callback.onResult(
                    isGranted = true,
                    grantedPermissions = grantedPermissions,
                    deniedPermissions = deniedList
                )
            } else {
                onPermissionsDenied(grantedPermissions, deniedList, callback)
            }
        }
    }

    /**
     * When at least one permission is denied
     */
    private fun onPermissionsDenied(
        grantedPermissions: MutableList<String>,
        deniedPermissions: MutableList<String>,
        callback: RuntimePermissionsResultCallback,
    ) {
        if (onNeverAskAgain != null
            && deniedPermissions.find {
                !source.shouldShowRequestPermissionRationale(it)
            } != null
        ) {
            val neverAskAgainScope = NeverAskAgainScopeImpl(
                source = source,
                callback = callback,
                grantedPermissions = grantedPermissions,
                deniedPermissions = deniedPermissions,
            )
            onNeverAskAgain!!.onNeverAskAgain(neverAskAgainScope)
        } else {
            callback.onResult(
                isGranted = false,
                grantedPermissions = grantedPermissions,
                deniedPermissions = deniedPermissions
            )
        }
    }

}