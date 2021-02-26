package com.github.offile.permissionshelper.core

abstract class Request<R: Result>(
    protected val source: Source,
) {
    abstract fun request(callback: ResultCallback<R>)
}