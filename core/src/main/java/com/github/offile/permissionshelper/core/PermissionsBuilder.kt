package com.github.offile.permissionshelper.core

abstract class PermissionsBuilder<Request : PermissionsRequest<Result>, Result : PermissionsResult>(
    protected val permissionsScope: PermissionsScope,
) {
    abstract fun build(): Request

    fun request(callback: PermissionsResultCallback<Result>){
        build().request(callback)
    }
}