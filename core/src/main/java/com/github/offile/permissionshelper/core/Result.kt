package com.github.offile.permissionshelper.core

open class Result(val isGranted: Boolean)

object DefaultResult {
    @JvmStatic
    val granted = Result(true)
    @JvmStatic
    val denied = Result(false)
}