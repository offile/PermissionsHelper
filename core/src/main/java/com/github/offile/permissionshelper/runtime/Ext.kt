package com.github.offile.permissionshelper.runtime

@Suppress("NOTHING_TO_INLINE")
inline fun RuntimePermissionsResultCallback.onResult(
    isGranted: Boolean,
    grantedPermissions: List<String>,
    deniedPermissions: List<String>,
){
    onResult(
        RuntimeResult(
            isGranted = isGranted,
            grantedList = grantedPermissions,
            deniedList = deniedPermissions
        )
    )
}