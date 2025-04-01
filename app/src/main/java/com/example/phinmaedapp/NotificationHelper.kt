package com.example.phinmaedapp

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query

class NotificationHelper(private val appContext: Context) {
    private val db = FirebaseFirestore.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun sendNotificationToAllUsers(
        title: String,
        message: String,
        callback: (Boolean, String) -> Unit
    ) {
        if (title.isBlank() || message.isBlank()) {
            callback(false, "Title and message cannot be empty")
            return
        }

        val senderEmail = auth.currentUser?.email ?: "admin"

        db.collection("users").get()
            .addOnSuccessListener { usersSnapshot ->
                val batch = db.batch()
                val notificationId = db.collection("notifications").document().id

                val globalNotificationRef = db.collection("notifications").document(notificationId)
                val globalNotification = hashMapOf(
                    "title" to title,
                    "message" to message,
                    "timestamp" to FieldValue.serverTimestamp(),
                    "sender" to senderEmail,
                    "notificationId" to notificationId
                )
                batch.set(globalNotificationRef, globalNotification)

                usersSnapshot.documents.forEach { userDocument ->
                    val userId = userDocument.id
                    val userNotificationRef = db.collection("user_notifications")
                        .document(userId)
                        .collection("notifications")
                        .document(notificationId)

                    val userNotification = hashMapOf(
                        "title" to title,
                        "message" to message,
                        "timestamp" to FieldValue.serverTimestamp(),
                        "sender" to senderEmail,
                        "read" to false,
                        "globalNotificationId" to notificationId
                    )
                    batch.set(userNotificationRef, userNotification)
                }

                batch.commit()
                    .addOnSuccessListener {
                        callback(true, "Notification sent to all users successfully")
                    }
                    .addOnFailureListener { e ->
                        callback(false, "Failed to send notifications: ${e.localizedMessage}")
                    }
            }
            .addOnFailureListener { e ->
                callback(false, "Failed to fetch users: ${e.localizedMessage}")
            }
    }

    fun getUserNotifications(userId: String, callback: (List<AppNotification>) -> Unit) {
        db.collection("user_notifications")
            .document(userId)
            .collection("notifications")
            .orderBy("timestamp", Query.Direction.DESCENDING)
            .addSnapshotListener { snapshot, error ->
                error?.let {
                    callback(emptyList())
                    return@addSnapshotListener
                }

                val notifications = mutableListOf<AppNotification>()
                snapshot?.documents?.forEach { document ->
                    notifications.add(
                        AppNotification(
                            id = document.id,
                            title = document.getString("title") ?: "",
                            message = document.getString("message") ?: "",
                            timestamp = document.getTimestamp("timestamp")?.toDate()?.time ?: 0,
                            sender = document.getString("sender") ?: "",
                            isRead = document.getBoolean("read") ?: false
                        )
                    )
                }
                callback(notifications)
            }
    }

    fun markNotificationAsRead(userId: String, notificationId: String, callback: (Boolean) -> Unit) {
        db.collection("user_notifications")
            .document(userId)
            .collection("notifications")
            .document(notificationId)
            .update("read", true)
            .addOnSuccessListener {
                callback(true)
            }
            .addOnFailureListener {
                callback(false)
            }
    }
}

// Move this to a separate file (e.g., models/AppNotification.kt)
