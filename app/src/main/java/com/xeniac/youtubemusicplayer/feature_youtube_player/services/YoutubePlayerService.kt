package com.xeniac.youtubemusicplayer.feature_youtube_player.services

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.xeniac.youtubemusicplayer.BaseApplication.Companion.NOTIFICATION_CHANNEL_ID_YOUTUBE_SERVICE
import com.xeniac.youtubemusicplayer.R
import com.xeniac.youtubemusicplayer.core.presentation.MainActivity

class YoutubePlayerService : Service() {

    enum class Actions {
        START,
        STOP
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(
        intent: Intent?,
        flags: Int,
        startId: Int
    ): Int {
        when (intent?.action) {
            Actions.START.toString() -> start()
            Actions.STOP.toString() -> stop()
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start() {
        val notification = NotificationCompat.Builder(
            /* context = */ this,
            /* channelId = */ NOTIFICATION_CHANNEL_ID_YOUTUBE_SERVICE
        ).apply {
            val launchMainActivityIntent = Intent(
                /* packageContext = */ this@YoutubePlayerService,
                /* cls = */ MainActivity::class.java
            ).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            }
            val openAppPendingIntent = PendingIntent.getActivity(
                /* context = */ this@YoutubePlayerService,
                /* requestCode = */ 0,
                /* intent = */ launchMainActivityIntent,
                /* flags = */ if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    PendingIntent.FLAG_IMMUTABLE else 0
            )

            setAutoCancel(false)
            setContentIntent(openAppPendingIntent)
            setOngoing(true) // Ongoing notifications cannot be dismissed by the user
            setSmallIcon(R.drawable.ic_notification)
            setContentTitle("YouTube is playing...")

            /*
            On Android 8.0 and above these values are ignored in favor of the values set on the notification's channel.
            On older platforms, these values are still used, so it is still required for apps supporting those platforms.
             */
            setPriority(NotificationCompat.PRIORITY_LOW)
            setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            setSound(null)
            setVibrate(null)
        }.build()

        startForeground(
            /* id = */ 1001,
            /* notification = */ notification
        )
    }

    private fun stop() {
        stopSelf()
    }
}