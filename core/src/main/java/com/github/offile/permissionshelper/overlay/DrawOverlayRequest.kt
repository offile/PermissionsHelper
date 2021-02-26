package com.github.offile.permissionshelper.overlay

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.github.offile.permissionshelper.core.*
import com.github.offile.permissionshelper.util.PermissionsUtil


/**
 * Check overlay permissions
 * @see Manifest.permission.SYSTEM_ALERT_WINDOW
 */
class DrawOverlayRequest(source: Source, private val onShowRationale: DefaultShowRationaleFun?) :
    Request<Result>(source) {

    private fun canDrawOverlays(): Boolean{
        return PermissionsUtil.canDrawOverlays(source.context)
    }

    override fun request(callback: ResultCallback<Result>) {
        if(canDrawOverlays()){
            callback.onResult(DefaultResult.granted)
        }else if (onShowRationale != null) {
            onShowRationale.onShowRationale(object : ShowRationaleScope {
                override fun proceed() {
                    requestOverlayPermission(callback)
                }

                override fun cancel() {
                    callback.onResult(DefaultResult.denied)
                }
            })
        }else {
            requestOverlayPermission(callback)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestOverlayPermission(callback: ResultCallback<Result>) {
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:" + source.packageName),
        )
        source.startActivityForResult(intent){ _, _ ->
            if(canDrawOverlays()){
                callback.onResult(DefaultResult.granted)
            }else{
                callback.onResult(DefaultResult.denied)
            }
        }
    }
}