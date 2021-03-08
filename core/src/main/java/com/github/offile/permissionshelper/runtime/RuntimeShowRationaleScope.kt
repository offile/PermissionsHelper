package com.github.offile.permissionshelper.runtime

import android.content.Context
import com.github.offile.permissionshelper.core.ShowRationaleScope

/**
 * @param permissions list of permissions that need show rationale
 */
abstract class RuntimeShowRationaleScope(
    context: Context,
    val permissions: List<String>,
) : ShowRationaleScope(context)