package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.example.phinmaedapp.databinding.FragmentUpangCalendarBinding
import java.util.Calendar


class UpangCalendarFragment : Fragment() {

    private lateinit var calendarView: com.applandeo.materialcalendarview.CalendarView
    private var events: MutableMap<String, String> = mutableMapOf()

    private var _binding: FragmentUpangCalendarBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpangCalendarBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        calendarView = binding.calendar

        val eventDays: ArrayList<EventDay> = ArrayList()

        // Add events with custom colors and icons
        addEvent(2025, Calendar.FEBRUARY, 18, "Welcome Assembly", eventDays)
        addEvent(2025, Calendar.MARCH, 17, "General Assembly", eventDays)
        addEvent(2025, Calendar.MARCH, 18, "General Assembly", eventDays)
        addEvent(2025, Calendar.APRIL, 20, "Another Event", eventDays)
        addEvent(2025, Calendar.MAY, 5, "Yet Another Event", eventDays)

        calendarView.setEvents(eventDays)

        calendarView.setOnCalendarDayClickListener(object : OnCalendarDayClickListener {
            override fun onClick(calendarDay: CalendarDay) {
                val day = calendarDay.calendar.get(Calendar.DAY_OF_MONTH)
                val month = calendarDay.calendar.get(Calendar.MONTH)
                val year = calendarDay.calendar.get(Calendar.YEAR)
                val formattedDate = String.format("%02d-%02d-%d", day, month + 1, year)

                if (events.containsKey(formattedDate)) {
                    Toast.makeText(
                        requireContext(),
                        events[formattedDate],
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    Toast.makeText(requireContext(), "No event", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun addEvent(year: Int, month: Int, day: Int, eventName: String, eventDays: ArrayList<EventDay>) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val eventDay = EventDay(calendar, R.drawable.twotone_event_24, ContextCompat.getColor(requireContext(), R.color.red))
        eventDays.add(eventDay)
        events[String.format("%02d-%02d-%d", day, month + 1, year)] = eventName
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}