package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.phinmaedapp.databinding.FragmentUpangAnnouncementsBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

class UpangAnnouncements : Fragment() {
    private var _binding: FragmentUpangAnnouncementsBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UpangHomeViewModel by viewModels() // Changed to AnnouncementsViewModel
    private lateinit var adapter: NotificationsAdapter
    private lateinit var auth: FirebaseAuth
    private lateinit var notificationHelper: NotificationHelper

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpangAnnouncementsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        notificationHelper = NotificationHelper(requireContext()) // Moved before setup

        setupRecyclerView()
        setupObservers()
        setupSwipeRefresh() // You forgot to call this
        loadNotifications()

        binding.fabMarkAllRead.setOnClickListener {
            markAllNotificationsRead()
        }
        binding.fabDeleteAll.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        binding.recyclerView.itemAnimator = DefaultItemAnimator().apply {
            addDuration = 200
            changeDuration = 200
            moveDuration = 200
        }
    }

    private fun setupRecyclerView() {
        adapter = NotificationsAdapter().apply {
            onItemClick = { notification ->
                if (!notification.isRead) {
                    auth.currentUser?.uid?.let { userId ->
                        notificationHelper.markNotificationAsRead(userId, notification.id) { success ->
                            if (success) {
                                val updatedList = currentList.toMutableList().apply {
                                    val index = indexOfFirst { it.id == notification.id }
                                    if (index != -1) {
                                        set(index, notification.copy(isRead = true))
                                    }
                                }
                                submitList(updatedList)
                            }
                        }
                    }
                }
                // Handle notification click
            }
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@UpangAnnouncements.adapter
            setHasFixedSize(true)
        }
    }

    private fun setupObservers() {
        viewModel.notifications.observe(viewLifecycleOwner) { notifications ->
            adapter.submitList(notifications)
            if (notifications.isEmpty()) {
                // Show empty state with animation
                binding.emptyStateContainer.visibility = View.VISIBLE
                binding.emptyStateContainer.alpha = 0f
                binding.emptyStateContainer.animate().alpha(1f).start()

                // Hide recycler view
                binding.recyclerView.visibility = View.GONE

                // Hide FABs
                binding.fabDeleteAll.visibility = View.GONE
                binding.fabMarkAllRead.visibility = View.GONE
            } else {
                // Show notifications
                binding.emptyStateContainer.visibility = View.GONE
                binding.recyclerView.visibility = View.VISIBLE
                adapter.submitList(notifications)

                // Show FABs as needed
                binding.fabDeleteAll.visibility = View.VISIBLE
                binding.fabMarkAllRead.visibility =
                    if (notifications.any { !it.isRead }) View.VISIBLE else View.GONE
            }
        }

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            binding.swipeRefreshLayout.isRefreshing = isLoading
        }

        viewModel.errorMessage.observe(viewLifecycleOwner) { message ->
            message?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_SHORT).show()
            }
        }
    }
    private fun markAllNotificationsRead() {
        auth.currentUser?.uid?.let { userId ->
            binding.fabMarkAllRead.hide()

            viewModel.markAllNotificationsRead(userId) { success ->
                activity?.runOnUiThread {
                    if (success) {
                        refreshNotifications()
                        Toast.makeText(context, "All marked as read", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to mark as read", Toast.LENGTH_SHORT).show()
                    }
                    binding.fabMarkAllRead.show()
                }
            }
        }
    }
    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Delete All Announcements")
            .setMessage("Are you sure you want to delete all announcements? This cannot be undone.")
            .setPositiveButton("Delete") { _, _ ->
                deleteAllAnnouncements()
            }
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    private fun deleteAllAnnouncements() {
        auth.currentUser?.uid?.let { userId ->
            // Show loading state
            binding.fabDeleteAll.hide()
            binding.swipeRefreshLayout.isRefreshing = true

            viewModel.deleteAllNotifications(userId) { success ->
                activity?.runOnUiThread {
                    binding.swipeRefreshLayout.isRefreshing = false

                    if (success) {
                        // Force empty state visibility
                        (binding.emptyStateContainer as View).visibility = View.VISIBLE
                        binding.recyclerView.visibility = View.GONE

                        // Hide FABs
                        binding.fabDeleteAll.visibility = View.GONE
                        binding.fabMarkAllRead.visibility = View.GONE

                        // Show confirmation
                        showEmptyStateWithAnimation()
                    } else {
                        Toast.makeText(context, "Deletion failed", Toast.LENGTH_SHORT).show()
                        binding.fabDeleteAll.show()
                    }
                }
            }
        }
    }

    private fun showEmptyStateWithAnimation() {
        // Fade in empty state
        binding.emptyStateContainer.apply {
            alpha = 0f
            visibility = View.VISIBLE
            animate()
                .alpha(1f)
                .setDuration(300)
                .start()
        }

        // Fade out recyclerview
        binding.recyclerView.animate()
            .alpha(0f)
            .setDuration(200)
            .withEndAction {
                binding.recyclerView.visibility = View.GONE
            }
            .start()
    }


    private fun setupSwipeRefresh() {
        binding.swipeRefreshLayout.setOnRefreshListener {
            refreshNotifications()
        }
    }

    private fun loadNotifications() {
        auth.currentUser?.uid?.let { userId ->
            viewModel.loadUserNotifications(userId)
        }
    }

    internal fun refreshNotifications() {
        auth.currentUser?.uid?.let { userId ->
            viewModel.loadUserNotifications(userId)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("School Announcements")
    }
}