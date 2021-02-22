package com.github.offile.permissionshelper.runtime

/**
 * You must call proceed or cancel to continue, otherwise the process will not complete
 * @see ShowRationaleFun
 */
interface ShowRationaleScope{

    /**
     * permissions list of permissions that need show rationale
     */
    val permissions: List<String>

    /**
     * continue the request
     */
    fun proceed()

    /**
     * Cancel and do not continue to apply for permission
     */
    fun cancel()
}