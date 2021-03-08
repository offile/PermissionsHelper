package com.github.offile.permissionshelper.notify

import com.github.offile.permissionshelper.core.*

class NotifyRequestBuilder(source: Source) :
    RationaleRequestBuilder<NotifyRequest, Result>(source) {

    override fun build(): NotifyRequest {
        return NotifyRequest(source, onShowRationale)
    }
}