package com.github.offile.permissionshelper.notify

import com.github.offile.permissionshelper.core.PermissionsResult
import com.github.offile.permissionshelper.core.RequestBuilder
import com.github.offile.permissionshelper.core.Source

class NotifyRequestBuilder(source: Source) :
    RequestBuilder<NotifyRequest, PermissionsResult>(source) {
    override fun build(): NotifyRequest {
        return NotifyRequest(source)
    }
}