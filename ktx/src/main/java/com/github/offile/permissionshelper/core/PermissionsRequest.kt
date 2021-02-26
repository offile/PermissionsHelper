package com.github.offile.permissionshelper.core

import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

suspend inline fun <Request : com.github.offile.permissionshelper.core.Request<Result>, Result : com.github.offile.permissionshelper.core.Result> RequestBuilder<Request, Result>.request(): Result {
    return suspendCancellableCoroutine {
        request { result ->
            it.resume(result)
        }
    }
}

suspend fun <Result : com.github.offile.permissionshelper.core.Result> Request<Result>.request(): Result {
    return suspendCancellableCoroutine {
        request { result ->
            it.resume(result)
        }
    }
}