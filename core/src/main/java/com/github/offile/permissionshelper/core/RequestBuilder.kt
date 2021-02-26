package com.github.offile.permissionshelper.core

abstract class RequestBuilder<Req : Request<R>, R : Result>(
    protected val source: Source,
) {
    abstract fun build(): Req

    fun request(callback: ResultCallback<R>){
        build().request(callback)
    }
}