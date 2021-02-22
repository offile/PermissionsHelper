package com.github.offile.permissionshelper.sample

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.github.offile.permissionshelper.PermissionsHelper

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.request).setOnClickListener {
            startRequestPermissions()
        }
    }

    private fun startRequestPermissions() {
        PermissionsHelper.with(this)
            .runtime()
            .permissions(Manifest.permission.CAMERA)
            .permissions(Manifest.permission.CALL_PHONE, Manifest.permission.RECORD_AUDIO,)
            .onShowRationale{
                AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Granted permission")
                    .setMessage("Next please grant permissions")
                    .setPositiveButton("ok"){_,_->
                        it.proceed()
                    }.setNegativeButton("cancel"){_,_->
                        it.cancel()
                    }.show()
            }.onNeverAskAgain{
                AlertDialog.Builder(this)
                    .setCancelable(false)
                    .setTitle("Permission denied")
                    .setMessage("You need to turn on these permissions in the settings.")
                    .setPositiveButton("ok"){_,_->
                        it.forwardToSettings()
                    }.setNegativeButton("cancel"){_,_->
                        it.cancel()
                    }.show()
            }.request{
                Toast.makeText(this, "this result is ${it.isGranted}", Toast.LENGTH_SHORT).show()
            }
    }
}