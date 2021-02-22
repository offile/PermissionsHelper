package com.github.offile.permissionshelper.ext

import com.github.offile.permissionshelper.core.PermissionsBuilder
import com.github.offile.permissionshelper.core.PermissionsRequest
import com.github.offile.permissionshelper.core.PermissionsResult
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