package ru.aleksandrorlove.appname.notification

import android.app.Notification
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.app.NotificationManagerCompat.IMPORTANCE_DEFAULT
import ru.aleksandrorlove.appname.App
import ru.aleksandrorlove.appname.R
import ru.aleksandrorlove.appname.model.Movie

class NotificationManager() {
    private val appContext = App.appContext
    private val notificationManager = NotificationManagerCompat.from(appContext)

    init {
        if (notificationManager.getNotificationChannel(CHANNEL_NEW_MOVIES) == null) {
            notificationManager.createNotificationChannel(
                NotificationChannelCompat.Builder(CHANNEL_NEW_MOVIES, IMPORTANCE_DEFAULT)
                    .setName(appContext.getString(R.string.channel_new_movie))
                    .setDescription(appContext.getString(R.string.channel_new_movie_description))
                    .build()
            )
        }
    }

    fun createNotification(movie: Movie) : Notification {
        return NotificationCompat.Builder(appContext, CHANNEL_NEW_MOVIES)
            .setSmallIcon(R.drawable.ic_baseline_message_24)
            .setContentTitle(movie.title)
            .setContentText(movie.overview)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()
    }

    fun showNotification(notification: Notification) {
        notificationManager.notify("", 1, notification)
    }

    companion object{
        private const val CHANNEL_NEW_MOVIES = "new_movies"

        private const val REQUEST_CONTENT = 1
    }

    object Singleton{
        val instance = NotificationManager()
    }

}