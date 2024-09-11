package com.xeniac.youtubemusicplayer

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationChannelGroup
import android.app.NotificationManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application() {

    companion object {
        const val NOTIFICATION_CHANNEL_GROUP_ID_YOUTUBE = "group_youtube"
        const val NOTIFICATION_CHANNEL_ID_YOUTUBE_SERVICE = "channel_youtube_service"
    }

    @Inject
    lateinit var notificationManager: NotificationManager

    override fun onCreate() {
        super.onCreate()

        setupTimber()
        setAppTheme()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createYouTubeNotificationChannelGroup()
            createYouTubeServiceNotificationChannel()
        }
    }

    private fun setupTimber() = Timber.plant(Timber.DebugTree())

    private fun setAppTheme() = AppCompatDelegate.setDefaultNightMode(
        /* mode = */ AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
    )

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createYouTubeNotificationChannelGroup() {
        val notificationChannelGroup = NotificationChannelGroup(
            /* id = */ NOTIFICATION_CHANNEL_GROUP_ID_YOUTUBE,
            /* name = */ getString(R.string.notification_youtube_channel_group_name)
        )

        notificationManager.createNotificationChannelGroup(notificationChannelGroup)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createYouTubeServiceNotificationChannel() {
        val channel = NotificationChannel(
            /* id = */ NOTIFICATION_CHANNEL_ID_YOUTUBE_SERVICE,
            /* name = */ getString(R.string.notification_youtube_channel_name_service),
            /* importance = */ NotificationManager.IMPORTANCE_LOW
        ).apply {
            group = NOTIFICATION_CHANNEL_GROUP_ID_YOUTUBE
            lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            enableLights(false)
        }

        notificationManager.createNotificationChannel(channel)
    }
}