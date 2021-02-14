package com.github.offile.permissionshelper.ext

import com.github.offile.permissionshelper.base.PermissionsBuilder
import com.github.offile.permissionshelper.base.PermissionsRequest
import com.github.offile.permissionshelper.base.PermissionsResult
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend fun <Request : PermissionsRequest<Result>, Result : PermissionsResult> PermissionsBuilder<Request, Result>.request(): Result {
    return suspendCancellableCoroutine {
        request { result ->
            it.resume(result)
        }
    }
}

suspend fun <Result : PermissionsResult> PermissionsRequest<Result>.request(): Result {
    return suspendCancellableCoroutine {
        request { result ->
            it.resume(result)
        }
    }
}