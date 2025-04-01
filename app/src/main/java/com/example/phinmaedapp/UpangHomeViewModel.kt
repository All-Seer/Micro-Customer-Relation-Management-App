package com.example.phinmaedapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore


class UpangHomeViewModel : ViewModel() {
    private val _notifications = MutableLiveData<List<AppNotification>>()
    val notifications: LiveData<List<AppNotification>> = _notifications
    private val db = FirebaseFirestore.getInstance()
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val notificationHelper = NotificationHelper(ApplicationClass.appContext)
    fun markAllNotificationsRead(userId: String, callback: (Boolean) -> Unit) {
        _isLoading.value = true

        db.collection("user_notifications")
            .document(userId)
            .collection("notifications")
            .whereEqualTo("read", false)
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    _isLoading.postValue(false)
                    callback(true)
                    return@addOnSuccessListener
                }

                val batch = db.batch()
                querySnapshot.documents.forEach { document ->
                    batch.update(document.reference, "read", true)
                }

                batch.commit()
                    .addOnSuccessListener {
                        _isLoading.postValue(false)
                        callback(true)
                    }
                    .addOnFailureListener { e ->
                        _errorMessage.postValue("Failed to mark all as read: ${e.message}")
                        _isLoading.postValue(false)
                        callback(false)
                    }
            }
            .addOnFailureListener { e ->
                _errorMessage.postValue("Failed to fetch notifications: ${e.message}")
                _isLoading.postValue(false)
                callback(false)
            }
    }

    fun deleteAllNotifications(userId: String, callback: (Boolean) -> Unit) {
        _isLoading.value = true

        db.collection("user_notifications")
            .document(userId)
            .collection("notifications")
            .get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.isEmpty) {
                    _notifications.postValue(emptyList()) // Clear local data
                    _isLoading.postValue(false)
                    callback(true)
                    return@addOnSuccessListener
                }

                val batch = db.batch()
                querySnapshot.documents.forEach { document ->
                    batch.delete(document.reference)
                }

                batch.commit()
                    .addOnSuccessListener {
                        _notifications.postValue(emptyList()) // Clear local data
                        _isLoading.postValue(false)
                        callback(true)
                    }
                    .addOnFailureListener { e ->
                        _errorMessage.postValue("Delete failed: ${e.message}")
                        _isLoading.postValue(false)
                        callback(false)
                    }
            }
            .addOnFailureListener { e ->
                _errorMessage.postValue("Fetch failed: ${e.message}")
                _isLoading.postValue(false)
                callback(false)
            }
    }

    // Primary method to load user-specific notifications
    fun loadUserNotifications(userId: String) {
        _isLoading.value = true
        _errorMessage.value = null

        notificationHelper.getUserNotifications(userId) { notifications ->
            _notifications.postValue(notifications)
            _isLoading.postValue(false)
        }
    }

}