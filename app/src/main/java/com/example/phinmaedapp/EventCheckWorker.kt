package com.example.phinmaedapp

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Calendar

class EventCheckWorker(context: Context, workerParams: WorkerParameters) : Worker(context, workerParams) {
    private val database: DatabaseReference = Firebase.database.reference

    override fun doWork(): Result {
        checkForUpcomingEvents()
        return Result.success()
    }

    private fun checkForUpcomingEvents() {
        val currentDate = Calendar.getInstance()
        val currentMonth = currentDate.get(Calendar.MONTH) + 1 // Months are 0-based in Calendar
        val currentYear = currentDate.get(Calendar.YEAR)

        database.child("events").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (eventSnapshot in snapshot.children) {
                    val event = eventSnapshot.getValue(CalendarEvent::class.java)
                    if (event != null) {
                        val dateParts = event.date.split("-")
                        val day = dateParts[0].toInt()
                        val month = dateParts[1].toInt()
                        val year = dateParts[2].toInt()

                        // Check if the event is in the current month and year
                        if (month == currentMonth && year == currentYear) {
                            sendNotification(event.name)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
            }
        })
    }

    private fun sendNotification(eventName: String) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "event_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Event Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notificationBuilder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.twotone_event_24)
            .setContentTitle("Upcoming Event")
            .setContentText(eventName)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        notificationManager.notify(1, notificationBuilder.build())
    }
}