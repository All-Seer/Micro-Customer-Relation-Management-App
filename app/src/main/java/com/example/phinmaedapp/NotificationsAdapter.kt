package com.example.phinmaedapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.phinmaedapp.databinding.ItemNotificationBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class NotificationsAdapter : ListAdapter<AppNotification, NotificationsAdapter.NotificationViewHolder>(
    NotificationDiffCallback()
) {
    var onItemClick: ((AppNotification) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationViewHolder {
        val binding = ItemNotificationBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return NotificationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotificationViewHolder, position: Int) {
        val notification = getItem(position)
        holder.bind(notification)

        holder.itemView.setOnClickListener {
            onItemClick?.invoke(notification)
        }
    }

    inner class NotificationViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                val notification = getItem(adapterPosition)
                onItemClick?.invoke(notification)
            }
        }

        fun bind(notification: AppNotification) {
            with(binding) {
                notificationTitle.text = notification.title
                notificationMessage.text = notification.message
                notificationTimestamp.text = formatDate(notification.timestamp)
                notificationSender.text = notification.sender

                if (notification.isRead) {
                    itemView.setBackgroundColor("#FFFFFF".toColorInt())
                } else {
                    itemView.setBackgroundColor("#bbc8af".toColorInt())
                }
            }
        }

        private fun formatDate(timestamp: Long): String {
            return SimpleDateFormat("MMM dd, yyyy hh:mm a", Locale.getDefault())
                .format(Date(timestamp))
        }
    }

    class NotificationDiffCallback : DiffUtil.ItemCallback<AppNotification>() {
        override fun areItemsTheSame(oldItem: AppNotification, newItem: AppNotification): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: AppNotification, newItem: AppNotification): Boolean {
            return oldItem == newItem
        }
    }
}