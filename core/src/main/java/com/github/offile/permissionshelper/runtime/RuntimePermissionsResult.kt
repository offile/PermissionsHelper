package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.core.PermissionsResult

class RuntimePermissionsResult(
    isGranted: Boolean,
    val grantedList: List<String> = emptyList(),
    val deniedList: List<String> = emptyList(),
) : PermissionsResult(isGranted)