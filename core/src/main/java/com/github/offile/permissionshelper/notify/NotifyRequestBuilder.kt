package com.github.offile.permissionshelper.notify

import com.github.offile.permissionshelper.core.*

class NotifyRequestBuilder(source: Source) :
    RequestBuilder<NotifyRequest, Result>(source) {

    private var onShowRationale: DefaultShowRationaleFun? = null

    fun onShowRationale(onShowRationale: DefaultShowRationaleFun): NotifyRequestBuilder {
        this.onShowRationale = onShowRationale
        return this
    }

    override fun build(): NotifyRequest {
        return NotifyRequest(source, onShowRationale)
    }
}