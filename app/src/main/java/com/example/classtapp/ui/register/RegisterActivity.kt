package com.example.classtapp.ui.register

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityMainBinding
import com.example.classtapp.databinding.ActivityRegisterBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding, RegisterViewModel>() {

    private val channelId = "channel notifikasi"
    private val notificationId = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_register)

        createNotificationChannel()

    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "Notification Title"
            val descriptionText = "Notification Description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }

    }

    private fun sendNotification() {

        val photoProfile =
            BitmapFactory.decodeResource(applicationContext.resources, R.drawable.photo_friend)

        val builder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_baseline_person)
            .setContentTitle("Notifikasi Register")
            .setContentText("Akun anda berhasil didaftarkan")
            .setLargeIcon(photoProfile)
            .setStyle(
                NotificationCompat.BigTextStyle()
                    .bigText("Akun anda sudah didaftarkan, silahkan untuk login dan segera cari teman!")
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(this)) {
            notify(notificationId, builder.build())
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvRegisterNotifikasi -> sendNotification()
        }

        super.onClick(v)
    }


}