package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.core.PermissionsResultCallback

typealias RuntimePermissionsResultCallback = PermissionsResultCallback<RuntimePermissionsResult>

typealias CheckPermissionsCallback = (
    isGranted: Boolean,
    grantedList: MutableList<String>,
    noGrantedList: MutableList<String>
) -> Unit