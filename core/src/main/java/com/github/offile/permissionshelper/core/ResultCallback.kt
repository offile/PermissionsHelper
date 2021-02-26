package com.github.offile.permissionshelper.core

fun interface ResultCallback<R: Result> {
    fun onResult(result: R)
}