package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.core.ShowRationaleScope

interface RuntimeShowRationaleScope : ShowRationaleScope {
    /**
     * permissions list of permissions that need show rationale
     */
    val permissions: List<String>
}