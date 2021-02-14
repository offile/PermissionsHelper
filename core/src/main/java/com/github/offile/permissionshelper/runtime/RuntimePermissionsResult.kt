package com.github.offile.permissionshelper.runtime

import com.github.offile.permissionshelper.base.PermissionsResult

data class RuntimePermissionsResult(
    override val isGranted: Boolean,
    val grantedList: List<String> = emptyList(),
    val deniedList: List<String> = emptyList(),
) : PermissionsResult