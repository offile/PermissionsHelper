package com.github.offile.permissionshelper

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.offile.activityresult.EasyActivityResult
import com.github.offile.activityresult.callback.PermissionsResultCallback
import com.github.offile.permissionshelper.util.PermissionsUtil

inline class PermissionsScope(private val easyActivityResult: EasyActivityResult) {

    val context: Context get() = easyActivityResult.fragment.requireContext()

    /**
     * check if permission is granted
     * @param permission name of the permission being checked
     */
    fun checkPermission(permission: String): Boolean{
        return PermissionsUtil.checkPermission(context, permission)
    }

    /**
     * Apply for runtime permission
     * @param permissions runtime permissions
     */
    fun requestPermissions(vararg permissions: String, callback: PermissionsResultCallback) {
        easyActivityResult.requestPermissions(*permissions, callback = callback)
    }

    /**
     * Gets whether you should show UI with rationale for requesting a permission.
     * @see Fragment.shouldShowRequestPermissionRationale
     */
    fun shouldShowRequestPermissionRationale(permission: String): Boolean{
        return easyActivityResult.fragment.shouldShowRequestPermissionRationale(permission)
    }

    /**
     * Request to jump to the application settings to open permissions
     */
    fun requestPermissionsBySettings(permissions: List<String>, callback: PermissionsResultCallback){
        easyActivityResult.startActivityForResult(Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.parse("package:${context.packageName}")),
        ){ _, _ ->
            val permissionArray = permissions.toTypedArray()
            val grantedResults = IntArray(permissionArray.size){
                val permission = permissionArray[it]
                ContextCompat.checkSelfPermission(context, permission)
            }
            callback.onRequestPermissionsResult(permissionArray, grantedResults)
        }
    }

}