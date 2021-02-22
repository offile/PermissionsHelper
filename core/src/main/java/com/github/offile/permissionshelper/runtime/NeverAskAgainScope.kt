package com.github.offile.permissionshelper.runtime

import android.content.pm.PackageManager
import com.github.offile.activityresult.callback.PermissionsResultCallback
import com.github.offile.permissionshelper.core.PermissionsScope

interface NeverAskAgainScope {
    fun forwardToSettings()
    fun cancel()
}

internal class NeverAskAgainScopeImpl(
    private val permissionsScope: PermissionsScope,
    private val callback: RuntimePermissionsResultCallback,
    private val grantedPermissions: MutableList<String>,
    private val deniedPermissions: MutableList<String>,
) : NeverAskAgainScope, PermissionsResultCallback {

    override fun forwardToSettings() {
        permissionsScope.requestPermissionsBySettings(deniedPermissions, this)
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