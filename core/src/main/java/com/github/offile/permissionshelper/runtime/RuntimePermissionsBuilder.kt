package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.core.PermissionsBuilder
import com.github.offile.permissionshelper.core.PermissionsScope

class RuntimePermissionsBuilder(
    permissionsScope: PermissionsScope,
) : PermissionsBuilder<RuntimePermissionsRequest, RuntimePermissionsResult>(permissionsScope) {

    private val permissions: LinkedHashSet<String>  = LinkedHashSet()
    private var onShowRationale: ShowRationaleFun? = null
    private var onNeverAskAgain: NeverAskAgainFun? = null

    fun permissions(vararg permission: String): RuntimePermissionsBuilder {
        permissions.addAll(permission)
        return this
    }

    fun onShowRationale(onShowRationale: ShowRationaleFun): RuntimePermissionsBuilder{
        this.onShowRationale = onShowRationale
        return this
    }

    fun onNeverAskAgain(onNeverAskAgain: NeverAskAgainFun): RuntimePermissionsBuilder{
        this.onNeverAskAgain = onNeverAskAgain
        return this
    }

    override fun build(): RuntimePermissionsRequest {
        return RuntimePermissionsRequest(
            permissionsScope = permissionsScope,
            permissions = permissions,
            onShowRationale = onShowRationale,
            onNeverAskAgain = onNeverAskAgain,
        )
    }

}