package com.github.offile.permissionshelper.notify

import com.github.offile.permissionshelper.core.*
import com.github.offile.permissionshelper.util.IntentUtil
import com.github.offile.permissionshelper.util.PermissionsUtil


class NotifyRequest(source: Source) :
    Request<PermissionsResult>(source) {

    override fun request(callback: PermissionsResultCallback<PermissionsResult>) {
        if(PermissionsUtil.areNotificationsEnabled(source.context)){
            callback.onResult(PermissionsResult(true))
        }else{
            val settingIntent = IntentUtil.getNotificationSettingsIntent(source.context)
            source.startActivityForResult(settingIntent){ _,_ ->
                if(PermissionsUtil.areNotificationsEnabled(source.context)){
                    callback.onResult(permissionGranted)
                }else{
                    callback.onResult(permissionDenied)
                }
            }
        }
    }
}