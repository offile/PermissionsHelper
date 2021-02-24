package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.core.RequestBuilder
import com.github.offile.permissionshelper.core.Source

class RuntimeRequestBuilder(
    source: Source,
) : RequestBuilder<RuntimeRequest, RuntimePermissionsResult>(source) {

    private val permissions: LinkedHashSet<String>  = LinkedHashSet()
    private var onShowRationale: ShowRationaleFun? = null
    private var onNeverAskAgain: NeverAskAgainFun? = null

    fun permissions(vararg permission: String): RuntimeRequestBuilder {
        permissions.addAll(permission)
        return this
    }

    fun onShowRationale(onShowRationale: ShowRationaleFun): RuntimeRequestBuilder{
        this.onShowRationale = onShowRationale
        return this
    }

    fun onNeverAskAgain(onNeverAskAgain: NeverAskAgainFun): RuntimeRequestBuilder{
        this.onNeverAskAgain = onNeverAskAgain
        return this
    }

    override fun build(): RuntimeRequest {
        return RuntimeRequest(
            source = source,
            permissions = permissions,
            onShowRationale = onShowRationale,
            onNeverAskAgain = onNeverAskAgain,
        )
    }

}