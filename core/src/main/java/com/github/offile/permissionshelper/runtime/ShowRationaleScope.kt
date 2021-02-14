package com.github.offile.permissionshelper.runtime

class ShowRationaleScope(
    val permissions: List<String>,
    private val request: Fun,
) {
    fun proceed(){
        request()
    }
}