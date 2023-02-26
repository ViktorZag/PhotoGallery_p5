package com.viktor_zet.photogallery_p5.utils

import android.app.PendingIntent
import android.content.Context
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.viktor_zet.photogallery_p5.R
import com.viktor_zet.photogallery_p5.app.NOTIFICATION_CHANNEL_ID
import com.viktor_zet.photogallery_p5.repository.PhotoRepository
import com.viktor_zet.photogallery_p5.repository.PreferencesRepository
import com.viktor_zet.photogallery_p5.ui.MainActivity
import kotlinx.coroutines.flow.first
import okhttp3.internal.notify

private const val TAG = "PollWorker"

class PollWorker(
    private val context: Context,
    workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {

        val preferencesRepository = PreferencesRepository.getInstance()
        val photoRepository = PhotoRepository()

        val query = preferencesRepository.storedQuery.first()
        val lastId = preferencesRepository.lastResultIt.first()
        if (query.isEmpty()) {
            Log.i(TAG, "No saved query")
            return Result.success()
        }
        return try {
            val items = photoRepository.searchPhotos(query)

            if (items.isNotEmpty()) {
                val newResultId = items.first().id
                if (newResultId == lastId) {
                    Log.i(TAG, "Still have the same result: $newResultId")
                } else {
                    Log.i(TAG, "Got a new result: $newResultId")
                    preferencesRepository.setLastResultId(newResultId)
                    notifyUser()
                }
            }
            Result.success()
        } catch (ex: Exception) {
            Log.e(TAG, "Background update failed", ex)
            Result.failure()
        }
    }

        private fun notifyUser(){
            val intent = MainActivity.newIntent(context)
            val pendingIntent = PendingIntent.getActivity(
                context,
                0,
                intent,
                PendingIntent.FLAG_IMMUTABLE
            )

            val resources=context.resources

            val notification = NotificationCompat
                .Builder(context, NOTIFICATION_CHANNEL_ID)
                .setTicker(resources.getString(R.string.new_pictures_title))
                .setSmallIcon(android.R.drawable.ic_menu_report_image)
                .setContentTitle(resources.getString(R.string.new_pictures_title))
                .setContentText(resources.getString(R.string.new_pictures_text))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()

            NotificationManagerCompat.from(context).notify(0,notification)
        }

}












