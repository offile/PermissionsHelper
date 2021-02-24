package com.github.offile.permissionshelper.core

abstract class Request<Result: PermissionsResult>(
    protected val source: Source,
) {
    abstract fun request(callback: PermissionsResultCallback<Result>)
}