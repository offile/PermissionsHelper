package com.github.offile.permissionshelper.vpn

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.VpnService
import com.github.offile.permissionshelper.core.*


/**
 * Check vpn permissions
 * @see Manifest.permission.BIND_VPN_SERVICE
 */
class VpnRequest(source: Source, private val onShowRationale: DefaultShowRationaleFun?) :
    Request<Result>(source) {

    override fun request(callback: ResultCallback<Result>) {
        val intent = VpnService.prepare(source.context)
        if(intent == null){
            callback.onResult(DefaultResult.granted)
        }else if (onShowRationale != null) {
            onShowRationale.onShowRationale(object : ShowRationaleScope(source.context) {
                override fun proceed() {
                    requestVpnPermission(intent, callback)
                }

                override fun cancel() {
                    callback.onResult(DefaultResult.denied)
                }
            })
        }else {
            requestVpnPermission(intent, callback)
        }
    }

    private fun requestVpnPermission(intent: Intent, callback: ResultCallback<Result>) {
        source.startActivityForResult(intent){ resultCode, _ ->
            if(resultCode == Activity.RESULT_OK){
                callback.onResult(DefaultResult.granted)
            }else{
                callback.onResult(DefaultResult.denied)
            }
        }
    }
}