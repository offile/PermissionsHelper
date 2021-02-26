package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.core.ResultCallback
import com.github.offile.permissionshelper.core.ShowRationaleFun

typealias RuntimePermissionsResultCallback = ResultCallback<RuntimeResult>

typealias RuntimeShowRationaleFun = ShowRationaleFun<RuntimeShowRationaleScope>

typealias CheckPermissionsCallback = (
    isGranted: Boolean,
    grantedList: MutableList<String>,
    noGrantedList: MutableList<String>
) -> Unit