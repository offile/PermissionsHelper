package com.github.offile.permissionshelper.base

import com.github.offile.permissionshelper.PermissionsResultCallback
import com.github.offile.permissionshelper.PermissionsScope

abstract class PermissionsRequest<Result: PermissionsResult>(
    protected val permissionsScope: PermissionsScope,
) {
    abstract fun request(callback: PermissionsResultCallback<Result>)
}