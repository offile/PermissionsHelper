package com.github.offile.permissionshelper.core

abstract class RequestBuilder<Req : Request<Result>, Result : PermissionsResult>(
    protected val source: Source,
) {
    abstract fun build(): Req

    fun request(callback: PermissionsResultCallback<Result>){
        build().request(callback)
    }
}