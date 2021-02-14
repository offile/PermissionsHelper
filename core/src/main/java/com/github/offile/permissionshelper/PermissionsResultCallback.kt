package com.github.offile.permissionshelper

import com.github.offile.permissionshelper.base.PermissionsResult

fun interface PermissionsResultCallback<Result: PermissionsResult> {
    fun onResult(result: Result)
}