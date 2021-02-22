package com.github.offile.permissionshelper.core

fun interface PermissionsResultCallback<Result: PermissionsResult> {
    fun onResult(result: Result)
}