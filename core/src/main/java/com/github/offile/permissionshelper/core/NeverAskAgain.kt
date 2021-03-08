package com.github.offile.permissionshelper.core

import android.content.Context

fun interface NeverAskAgainFun {
    fun onNeverAskAgain(scope: NeverAskAgainScope)
}

abstract class NeverAskAgainScope(val context: Context) {
    abstract fun forwardToSettings()
    abstract fun cancel()
}