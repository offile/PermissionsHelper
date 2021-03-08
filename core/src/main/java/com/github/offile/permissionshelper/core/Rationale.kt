package com.github.offile.permissionshelper.core

import android.content.Context

fun interface ShowRationaleFun<Scope: ShowRationaleScope> {
    fun onShowRationale(scope: Scope)
}

/**
 * You must call proceed or cancel to continue, otherwise the process will not complete
 * @see ShowRationaleFun
 */
abstract class ShowRationaleScope(val context: Context){

    /**
     * continue the request
     */
    abstract fun proceed()

    /**
     * Cancel and do not continue to apply for permission
     */
    abstract fun cancel()
}