package com.github.offile.permissionshelper.runtime

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.github.offile.activityresult.callback.PermissionsResultCallback
import com.github.offile.permissionshelper.core.Source
import com.github.offile.permissionshelper.util.IntentUtil

interface NeverAskAgainScope {
    fun forwardToSettings()
    fun cancel()
}

internal class NeverAskAgainScopeImpl(
    private val source: Source,
    private val callback: RuntimePermissionsResultCallback,
    private val grantedPermissions: MutableList<String>,
    private val deniedPermissions: MutableList<String>,
) : NeverAskAgainScope, PermissionsResultCallback {

    override fun forwardToSettings() {
        requestPermissionsBySettings(deniedPermissions, this)
    }

    /**
     * Request to jump to the application settings to open permissions
     */
    fun requestPermissionsBySettings(
        permissions: List<String>,
        callback: PermissionsResultCallback
    ) {
        source.startActivityForResult(IntentUtil.getSettingsIntent(source.context)) { _, _ ->
            val permissionArray = permissions.toTypedArray()
            val grantedResults = IntArray(permissionArray.size) {
                val permission = permissionArray[it]
                ContextCompat.checkSelfPermission(source.context, permission)
            }
            callback.onRequestPermissionsResult(permissionArray, grantedResults)
        }
    }

    override fun cancel() {
        callback.onResult(
            isGranted = false,
            grantedPermissions = grantedPermissions,
            deniedPermissions = deniedPermissions,
        )
    }

    override fun onRequestPermissionsResult(permissions: Array<String>, grantResults: IntArray) {
        permissions.forEachIndexed { index, s ->
            if (grantResults[index] == PackageManager.PERMISSION_GRANTED) {
                grantedPermissions.add(s)
                deniedPermissions.remove(s)
            }
        }
        val isGranted = deniedPermissions.isEmpty()
        callback.onResult(
            isGranted = isGranted,
            grantedPermissions = grantedPermissions,
            deniedPermissions = deniedPermissions,
        )
    }

}