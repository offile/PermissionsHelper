package com.github.offile.permissionshelper.runtime

import android.content.pm.PackageManager
import com.github.offile.permissionshelper.PermissionsResultCallback
import com.github.offile.permissionshelper.PermissionsScope
import com.github.offile.permissionshelper.base.PermissionsRequest
import java.util.*
import kotlin.collections.ArrayList
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

class RuntimePermissionsRequest(
    permissionsScope: PermissionsScope,
    private val permissions: LinkedHashSet<String>,
    private var onShowRationale: ShowRationaleFun? = null,
    private var onNeverAskAgain: NeverAskAgainFun? = null,
) : PermissionsRequest<RuntimePermissionsResult>(permissionsScope) {

    /**
     * check permissions
     */
    @OptIn(ExperimentalContracts::class)
    private inline fun checkPermissions(
        permissions: Set<String>,
        callback: (isGranted: Boolean, grantedList: MutableList<String>, noGrantedList: MutableList<String>) -> Unit,
    ) {
        contract {
            callsInPlace(callback, InvocationKind.EXACTLY_ONCE)
        }
        val grantedList = ArrayList<String>()
        val noGrantedList = ArrayList<String>()
        permissions.forEach {
            if (permissionsScope.checkPermission(it)) {
                grantedList.add(it)
            } else {
                noGrantedList.add(it)
            }
        }
        val isGranted = grantedList.size == permissions.size
        callback(isGranted, grantedList, noGrantedList)
    }

    override fun request(callback: PermissionsResultCallback<RuntimePermissionsResult>) {
        checkPermissions(permissions) { isGranted, grantedList, noGrantedList ->
            if (isGranted) {
                // All permissions are granted
                callback.onResult(RuntimePermissionsResult(true, grantedList = grantedList))
            } else {
                // there are still permissions not granted
                requestNoGrantedPermissions(grantedList, noGrantedList, callback)
            }
        }
    }

    /**
     * Check if you want to explain before requesting permission
     */
    private fun requestBeforeCheckRationale(
        grantedList: MutableList<String>,
        noGrantedList: MutableList<String>,
        callback: PermissionsResultCallback<RuntimePermissionsResult>
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
                noGrantedList.filter(permissionsScope::shouldShowRequestPermissionRationale)
            if (rationaleList.isNotEmpty()) {
                onShowRationale!!.onShowRationale(ShowRationaleScope(rationaleList, requestFun))
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
        callback: PermissionsResultCallback<RuntimePermissionsResult>,
    ) {
        permissionsScope.requestPermissions(*noGrantedPermissions.toTypedArray()) { p, grantResults ->
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
                    RuntimePermissionsResult(
                        isGranted = true,
                        grantedList = grantedPermissions,
                        deniedList = deniedList
                    )
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
        callback: PermissionsResultCallback<RuntimePermissionsResult>,
    ) {
        if (onNeverAskAgain != null
            && deniedPermissions.find {
                !permissionsScope.shouldShowRequestPermissionRationale(it)
            } != null
        ) {
            val neverAskAgainScope = object : NeverAskAgainScope {
                override fun forwardToSettings() {
                    permissionsScope.requestPermissionsBySettings(deniedPermissions) { p, result ->
                        p.forEachIndexed { index, s ->
                            if (result[index] == PackageManager.PERMISSION_GRANTED) {
                                grantedPermissions.add(s)
                                deniedPermissions.remove(s)
                            }
                        }
                        val isGranted = deniedPermissions.isEmpty()
                        callback.onResult(
                            RuntimePermissionsResult(
                                isGranted = isGranted,
                                grantedList = grantedPermissions,
                                deniedList = deniedPermissions
                            )
                        )
                    }
                }

                override fun cancel() {
                    callback.onResult(
                        RuntimePermissionsResult(
                            isGranted = false,
                            grantedList = grantedPermissions,
                            deniedList = deniedPermissions
                        )
                    )
                }

            }
            onNeverAskAgain!!.onNeverAskAgain(neverAskAgainScope)
        } else {
            callback.onResult(
                RuntimePermissionsResult(
                    isGranted = false,
                    grantedList = grantedPermissions,
                    deniedList = deniedPermissions
                )
            )
        }
    }

}