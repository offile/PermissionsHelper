package com.github.offile.permissionshelper.core

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.offile.activityresult.EasyActivityResult
import com.github.offile.activityresult.callback.ActivityResultCallback
import com.github.offile.activityresult.callback.PermissionsResultCallback
import com.github.offile.permissionshelper.util.PermissionsUtil

/**
 * The source of the request.
 */
interface Source {

    val context: Context

    val packageName: String

    val packageManager: PackageManager

    /**
     * Check if runtime permissions are granted
     * @param permission name of the permission being checked
     */
    fun checkPermission(permission: String): Boolean

    /**
     * Apply for runtime permission
     * @param permissions runtime permissions
     */
    fun requestPermissions(vararg permissions: String, callback: PermissionsResultCallback)

    /**
     * Gets whether you should show UI with rationale for requesting a permission.
     * @see Fragment.shouldShowRequestPermissionRationale
     */
    fun shouldShowRequestPermissionRationale(permission: String): Boolean

    fun startActivityForResult(intent: Intent, callback: ActivityResultCallback)
}

abstract class BaseSource(
    private val easyActivityResult: EasyActivityResult
) : Source {

    override val packageName: String
        get() = context.packageName

    override val packageManager: PackageManager
        get() = context.packageManager

    override fun checkPermission(permission: String): Boolean {
        return PermissionsUtil.checkPermission(context, permission)
    }

    override fun requestPermissions(vararg permissions: String, callback: PermissionsResultCallback) {
        easyActivityResult.requestPermissions(*permissions, callback = callback)
    }

    override fun startActivityForResult(intent: Intent, callback: ActivityResultCallback) {
        easyActivityResult.startActivityForResult(intent, callback)
    }
}

class ActivitySource(private val activity: FragmentActivity) :
    BaseSource(EasyActivityResult.with(activity)) {
    override val context: Context
        get() = activity

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)
    }
}

class FragmentSource(private val fragment: Fragment) :
    BaseSource(EasyActivityResult.with(fragment)) {
    override val context: Context
        get() = fragment.requireContext()

    override fun shouldShowRequestPermissionRationale(permission: String): Boolean {
        return fragment.shouldShowRequestPermissionRationale(permission)
    }
}