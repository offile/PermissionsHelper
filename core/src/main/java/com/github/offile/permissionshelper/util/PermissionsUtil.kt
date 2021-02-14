package com.github.offile.permissionshelper.util

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat

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
}