package com.github.offile.permissionshelper.runtime

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