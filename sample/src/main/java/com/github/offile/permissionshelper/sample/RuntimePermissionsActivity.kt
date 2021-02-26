package com.github.offile.permissionshelper.sample

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.CameraView
import com.github.offile.permissionshelper.PermissionsHelper
import com.github.offile.permissionshelper.runtime.NeverAskAgainFun
import com.github.offile.permissionshelper.runtime.NeverAskAgainScope
import com.github.offile.permissionshelper.core.ShowRationaleFun
import com.github.offile.permissionshelper.core.ShowRationaleScope
import com.github.offile.permissionshelper.runtime.RuntimeShowRationaleFun
import com.github.offile.permissionshelper.runtime.RuntimeShowRationaleScope


class RuntimePermissionsActivity : AppCompatActivity(R.layout.activity_runtime_permissions),
    RuntimeShowRationaleFun, NeverAskAgainFun {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        PermissionsHelper.with(this)
            .runtime()
            .permissions(Manifest.permission.CAMERA)
            .permissions(Manifest.permission.RECORD_AUDIO)
            .onShowRationale(this)
            .onNeverAskAgain(this)
            .request{
                if(it.isGranted){
                    setupCamera()
                }else{
                    finish()
                }
            }
    }

    @SuppressLint("MissingPermission")
    private fun setupCamera() {
        findViewById<CameraView>(R.id.camera)
            .bindToLifecycle(this)
    }

    override fun onShowRationale(scope: RuntimeShowRationaleScope) {
        AlertDialog.Builder(this)
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

    override fun onNeverAskAgain(scope: NeverAskAgainScope) {
        AlertDialog.Builder(this)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}