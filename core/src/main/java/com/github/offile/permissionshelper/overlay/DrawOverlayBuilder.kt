package com.github.offile.permissionshelper.overlay

import com.github.offile.permissionshelper.core.RequestBuilder
import com.github.offile.permissionshelper.core.PermissionsResult
import com.github.offile.permissionshelper.core.Source

class DrawOverlayBuilder(source: Source) :
    RequestBuilder<DrawOverlayRequest, PermissionsResult>(source) {
    override fun build(): DrawOverlayRequest {
        return DrawOverlayRequest(source)
    }
}