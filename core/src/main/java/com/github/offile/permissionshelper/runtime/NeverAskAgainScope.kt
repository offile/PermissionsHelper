package com.github.offile.permissionshelper.runtime

interface NeverAskAgainScope {
    fun forwardToSettings()
    fun cancel()
}