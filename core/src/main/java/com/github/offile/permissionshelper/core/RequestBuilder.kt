package com.github.offile.permissionshelper.core


abstract class RequestBuilder<Req : Request<R>, R : Result>(
    protected val source: Source,
) {
    abstract fun build(): Req

    fun request(callback: ResultCallback<R>) {
        build().request(callback)
    }
}

abstract class RationaleRequestBuilder<Req : Request<R>, R : Result>(source: Source) :
    RequestBuilder<Req, R>(source) {
    protected var onShowRationale: DefaultShowRationaleFun? = null

    fun onShowRationale(onShowRationale: DefaultShowRationaleFun): RationaleRequestBuilder<Req, R> {
        this.onShowRationale = onShowRationale
        return this
    }
}