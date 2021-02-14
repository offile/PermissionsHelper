package com.github.offile.permissionshelper.runtime

fun interface NeverAskAgainFun {
    fun onNeverAskAgain(scope: NeverAskAgainScope)
}