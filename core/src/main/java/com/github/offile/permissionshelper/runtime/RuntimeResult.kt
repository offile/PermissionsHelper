package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.core.Result

class RuntimeResult(
    isGranted: Boolean,
    val grantedList: List<String> = emptyList(),
    val deniedList: List<String> = emptyList(),
) : Result(isGranted)