package com.github.offile.permissionshelper.core

/**
 * You must call proceed or cancel to continue, otherwise the process will not complete
 * @see ShowRationaleFun
 */
interface ShowRationaleScope{

    /**
     * continue the request
     */
    fun proceed()

    /**
     * Cancel and do not continue to apply for permission
     */
    fun cancel()
}