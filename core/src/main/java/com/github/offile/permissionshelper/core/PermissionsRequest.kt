package com.github.offile.permissionshelper.core

abstract class PermissionsRequest<Result: PermissionsResult>(
    protected val permissionsScope: PermissionsScope,
) {
    abstract fun request(callback: PermissionsResultCallback<Result>)
}