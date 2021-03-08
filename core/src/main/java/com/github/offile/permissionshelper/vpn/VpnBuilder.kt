package com.github.offile.permissionshelper.vpn

import com.github.offile.permissionshelper.core.*

class VpnBuilder(source: Source) :
    RationaleRequestBuilder<VpnRequest, Result>(source) {

    override fun build(): VpnRequest {
        return VpnRequest(source, onShowRationale)
    }

}