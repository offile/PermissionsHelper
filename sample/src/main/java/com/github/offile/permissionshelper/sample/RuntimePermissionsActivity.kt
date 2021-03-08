package com.github.offile.permissionshelper.sample

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.CameraView
import com.github.offile.permissionshelper.PermissionsHelper

class RuntimePermissionsActivity : AppCompatActivity(R.layout.activity_runtime_permissions) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        PermissionsHelper.with(this)
            .runtime()
            .permissions(Manifest.permission.CAMERA)
            .permissions(Manifest.permission.RECORD_AUDIO)
            .onShowRationale(PermissionsDialogs::showRationaleDialog)
            .onNeverAskAgain(PermissionsDialogs::showNeverAskAgainDialog)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == android.R.id.home){
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}