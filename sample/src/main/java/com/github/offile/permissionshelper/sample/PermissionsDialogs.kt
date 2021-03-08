package com.github.offile.permissionshelper.sample

import androidx.appcompat.app.AlertDialog
import com.github.offile.permissionshelper.core.NeverAskAgainScope
import com.github.offile.permissionshelper.runtime.RuntimeShowRationaleScope

object PermissionsDialogs {
    fun showRationaleDialog(scope: RuntimeShowRationaleScope){
        AlertDialog.Builder(scope.context)
            .setCancelable(false)
            .setTitle("Granted permission")
            .setMessage("Next please grant permissions")
            .setPositiveButton("ok"){_,_->
                // proceed request
                scope.proceed()
            }.setNegativeButton("cancel"){_,_->
                // cancel request
                scope.cancel()
            }.show()
    }

    fun showNeverAskAgainDialog(scope: NeverAskAgainScope){
        AlertDialog.Builder(scope.context)
            .setCancelable(false)
            .setTitle("Permission denied")
            .setMessage("You need to turn on these permissions in the settings.")
            .setPositiveButton("ok"){_,_->
                // Jump to the application details to open the permissions ,
                // and the process will continue when the user returns
                scope.forwardToSettings()
            }.setNegativeButton("cancel"){_,_->
                // cancel request
                scope.cancel()
            }.show()
    }
}