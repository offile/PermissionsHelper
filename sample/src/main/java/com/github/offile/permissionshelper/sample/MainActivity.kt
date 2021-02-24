package com.github.offile.permissionshelper.sample

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationChannel.DEFAULT_CHANNEL_ID
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.PermissionChecker
import com.github.offile.permissionshelper.PermissionsHelper

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private val permissionsHelper = PermissionsHelper.with(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<Button>(R.id.runtime).setOnClickListener { requestRuntimePermissions() }
        findViewById<Button>(R.id.overlay).setOnClickListener { requestOverlayPermission() }
        findViewById<Button>(R.id.notify).setOnClickListener { requestNotifyPermission() }
    }

    private fun requestRuntimePermissions() {
        startActivity(Intent(this, RuntimePermissionsActivity::class.java))
    }

    private fun requestOverlayPermission(){
        permissionsHelper
            .drawOverlay()
            .request{
                Toast.makeText(this, "this result is ${it.isGranted}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun requestNotifyPermission() {
        permissionsHelper
            .notify()
            .request{
                if(it.isGranted){
                    val notificationManager = NotificationManagerCompat.from(this)
                    // create channel
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        if(notificationManager.getNotificationChannel(packageName) == null){
                            val channel = NotificationChannel(
                                packageName,
                                DEFAULT_CHANNEL_ID,
                                NotificationManager.IMPORTANCE_HIGH)
                            notificationManager.createNotificationChannel(channel)
                        }
                    }
                    // create Notification
                    val notification = NotificationCompat.Builder(this, packageName)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle("test title")
                        .setContentText("content text")
                        .build()
                    notificationManager.notify(0, notification)
                }
            }
    }
}