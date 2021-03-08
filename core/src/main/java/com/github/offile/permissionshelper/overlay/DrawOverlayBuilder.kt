package com.github.offile.permissionshelper.overlay

import com.github.offile.permissionshelper.core.*

class DrawOverlayBuilder(source: Source) :
    RationaleRequestBuilder<DrawOverlayRequest, Result>(source) {

    override fun build(): DrawOverlayRequest {
        return DrawOverlayRequest(source, onShowRationale)
    }
}