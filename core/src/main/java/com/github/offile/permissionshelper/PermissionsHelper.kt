package com.github.offile.permissionshelper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.offile.permissionshelper.core.ActivitySource
import com.github.offile.permissionshelper.core.FragmentSource
import com.github.offile.permissionshelper.core.Source
import com.github.offile.permissionshelper.notify.NotifyRequestBuilder
import com.github.offile.permissionshelper.overlay.DrawOverlayBuilder
import com.github.offile.permissionshelper.runtime.RuntimeRequestBuilder

/**
 * Used to handle various permissions for Android
 */
class PermissionsHelper {
    companion object {
        @JvmStatic
        fun with(fragmentActivity: FragmentActivity): PermissionsHelper{
            return PermissionsHelper(fragmentActivity)
        }
        @JvmStatic
        fun with(fragment: Fragment): PermissionsHelper{
            return PermissionsHelper(fragment)
        }
    }

    private val source: Source

    private constructor(fragmentActivity: FragmentActivity){
        source = ActivitySource(fragmentActivity)
    }

    private constructor(fragment: Fragment){
        source = FragmentSource(fragment)
    }

    /**
     * Build runtime permissions request
     */
    fun runtime(): RuntimeRequestBuilder {
        return RuntimeRequestBuilder(source)
    }

    /**
     * Build Overlay permissions request
     */
    fun drawOverlay(): DrawOverlayBuilder {
        return DrawOverlayBuilder(source)
    }

    /**
     * build Notification permission request
     */
    fun notify(): NotifyRequestBuilder {
        return NotifyRequestBuilder(source)
    }
}