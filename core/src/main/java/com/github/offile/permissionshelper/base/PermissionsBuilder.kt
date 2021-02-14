package com.github.offile.permissionshelper.base

import com.github.offile.permissionshelper.PermissionsResultCallback
import com.github.offile.permissionshelper.PermissionsScope

abstract class PermissionsBuilder<Request : PermissionsRequest<Result>, Result : PermissionsResult>(
    protected val permissionsScope: PermissionsScope,
) {
    abstract fun build(): Request

    fun request(callback: PermissionsResultCallback<Result>){
        build().request(callback)
    }
}