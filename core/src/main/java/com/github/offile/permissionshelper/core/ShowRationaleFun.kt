package com.github.offile.permissionshelper.core

fun interface ShowRationaleFun<Scope: ShowRationaleScope> {
    fun onShowRationale(scope: Scope)
}