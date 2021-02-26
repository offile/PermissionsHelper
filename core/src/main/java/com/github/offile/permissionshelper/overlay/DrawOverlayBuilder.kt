package com.github.offile.permissionshelper.overlay

import com.github.offile.permissionshelper.core.DefaultShowRationaleFun
import com.github.offile.permissionshelper.core.RequestBuilder
import com.github.offile.permissionshelper.core.Result
import com.github.offile.permissionshelper.core.Source

class DrawOverlayBuilder(source: Source) :
    RequestBuilder<DrawOverlayRequest, Result>(source) {

    private var onShowRationale: DefaultShowRationaleFun? = null

    fun onShowRationale(onShowRationale: DefaultShowRationaleFun): DrawOverlayBuilder {
        this.onShowRationale = onShowRationale
        return this
    }

    override fun build(): DrawOverlayRequest {
        return DrawOverlayRequest(source, onShowRationale)
    }
}