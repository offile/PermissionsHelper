package com.github.offile.permissionshelper

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.github.offile.permissionshelper.runtime.RuntimePermissionsBuilder

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

    private val permissionsScope: PermissionsScope

    private constructor(fragmentActivity: FragmentActivity){
        permissionsScope = ActivityPermissionsScope(fragmentActivity)
    }

    private constructor(fragment: Fragment){
        permissionsScope = FragmentPermissionsScope(fragment)
    }

    /**
     * Build runtime permissions request
     */
    fun runtime(): RuntimePermissionsBuilder {
        return RuntimePermissionsBuilder(permissionsScope)
    }
}