package com.github.offile.permissionshelper.core

open class PermissionsResult(val isGranted: Boolean)

val permissionGranted = PermissionsResult(true)

val permissionDenied = PermissionsResult(false)