package com.github.offile.permissionshelper.notify

import com.github.offile.permissionshelper.core.*
import com.github.offile.permissionshelper.util.IntentUtil
import com.github.offile.permissionshelper.util.PermissionsUtil


class NotifyRequest(source: Source, private val onShowRationale: DefaultShowRationaleFun?) :
    Request<Result>(source) {

    override fun request(callback: ResultCallback<Result>) {
        if(PermissionsUtil.areNotificationsEnabled(source.context)){
            callback.onResult(Result(true))
        }else if(onShowRationale != null){
            onShowRationale.onShowRationale(object : ShowRationaleScope {

                override fun proceed() {
                    requestNotify(callback)
                }

                override fun cancel() {
                    callback.onResult(Result(false))
                }

            })
        }else{
            requestNotify(callback)
        }
    }

    private fun requestNotify(callback: ResultCallback<Result>){
        val settingIntent = IntentUtil.getNotificationSettingsIntent(source.context)
        source.startActivityForResult(settingIntent){ _,_ ->
            if(PermissionsUtil.areNotificationsEnabled(source.context)){
                callback.onResult(DefaultResult.granted)
            }else{
                callback.onResult(DefaultResult.denied)
            }
        }
    }
}