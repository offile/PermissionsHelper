package com.github.offile.permissionshelper.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.provider.Settings
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import kotlin.contracts.contract

object PermissionsUtil {

    /**
     * check if permission is granted
     * @param context Context
     * @param permission name of the permission being checked
     */
    fun checkPermission(context: Context, permission: String): Boolean {
        val result = ContextCompat.checkSelfPermission(context, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Check overlay permissions
     * @see Settings.canDrawOverlays
     */
    fun canDrawOverlays(context: Context): Boolean{
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M
                || Settings.canDrawOverlays(context)
    }

    fun areNotificationsEnabled(context: Context): Boolean{
        return NotificationManagerCompat.from(context)
            .areNotificationsEnabled()
    }
}