package com.example.phinmaedapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.applandeo.materialcalendarview.CalendarDay
import com.applandeo.materialcalendarview.EventDay
import com.applandeo.materialcalendarview.listeners.OnCalendarDayClickListener
import com.example.phinmaedapp.databinding.FragmentUpangEventPageBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import java.util.Calendar


class UpangEventPageFragment : Fragment() {

    private var _binding: FragmentUpangEventPageBinding? = null
    private val binding get() = _binding!!
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
    private lateinit var calendarView: com.applandeo.materialcalendarview.CalendarView
    private var events: MutableMap<String, String> = mutableMapOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUpangEventPageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // Initialize the bottom sheet
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomsheet) // Use bottomsheet ID
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED

        bottomSheetBehavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_EXPANDED -> {
                        // Bottom sheet is fully expanded
                    }
                    BottomSheetBehavior.STATE_HIDDEN -> {
                        // Bottom sheet is hidden
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // Handle slide events (optional)
            }
        })


        calendarView = binding.calendar

        val eventDays: ArrayList<EventDay> = ArrayList()

        addEvent(2025, Calendar.JANUARY, 3, "Eucharistic Celebration", eventDays)
        addEvent(2025, Calendar.JANUARY, 9, "Strand Day", eventDays)
        addEvent(2025, Calendar.JANUARY, 6, "P1 Examination (Upper Years)", eventDays)
        addEvent(2025, Calendar.JANUARY, 14, "Seminar For the DSPC", eventDays)
        addEvent(2025, Calendar.JANUARY, 16, "Tree Planting", eventDays)
        addEvent(2025, Calendar.JANUARY, 17, "Seminar Calctech Safety", eventDays)
        addEvent(2025, Calendar.JANUARY, 23, "Book Disclosure Gathering", eventDays)

        addEvent(2025, Calendar.FEBRUARY, 3, "Eucharistic Celebration", eventDays)
        addEvent(2025, Calendar.FEBRUARY, 5, "Outreach Program", eventDays)
        addEvent(2025, Calendar.FEBRUARY, 7, "Mass Blood Donation", eventDays)
        addEvent(2025, Calendar.FEBRUARY, 11, "Thanks Giving Mass", eventDays)
        addEvent(2025, Calendar.FEBRUARY, 13, "Valentine's Day", eventDays)
        addEvent(2025, Calendar.FEBRUARY, 14, "10TH FDC", eventDays)
        addEvent(2025, Calendar.FEBRUARY, 10, "P2 Examination (Upperclassmen)", eventDays)

        addEvent(2025, Calendar.MARCH, 5, "Ash Wednesday", eventDays)
        addEvent(2025, Calendar.MARCH, 6, "Caption Multimedia Club Event", eventDays)
        addEvent(2025, Calendar.MARCH, 3, "P2 Examination (Freshmen)", eventDays)
        addEvent(2025, Calendar.MARCH, 13, "Tourism Day", eventDays)
        addEvent(2025, Calendar.MARCH, 14, "JPIA DAY", eventDays)
        addEvent(2025, Calendar.MARCH, 24, "P3 Examination (Upper Years/Non-Graduating and gr 11)", eventDays)

        addEvent(2025, Calendar.APRIL, 4, "Eucharistic Celebration", eventDays)
        addEvent(2025, Calendar.APRIL, 7, "P3 Examination (Freshmen / gr 11)", eventDays)
        addEvent(2025, Calendar.APRIL, 15, "Criminology Testimonial", eventDays)
        addEvent(2025, Calendar.APRIL, 25, "Pulse Award", eventDays)

        addEvent(2025, Calendar.JUNE, 24, "First Week Hi", eventDays)
        addEvent(2025, Calendar.JUNE, 28, "KUDOS", eventDays)

        addEvent(2025, Calendar.JULY, 15, "First Week Hi V.2", eventDays)
        addEvent(2025, Calendar.JULY, 17, "Welcome Assembly", eventDays)
        addEvent(2025, Calendar.JULY, 18, "General Assembly", eventDays)
        addEvent(2025, Calendar.JULY, 19, "Bridging The Gaps", eventDays)
        addEvent(2025, Calendar.JULY, 25, "Bridging The Gap 2.0", eventDays)
        addEvent(2025, Calendar.JULY, 26, "General Assembly GPA  and Tactical 26", eventDays)
        addEvent(2025, Calendar.JULY, 31, "Nutrition Month Celebration (TVL)", eventDays)

        addEvent(2025, Calendar.AUGUST, 1, "Convention PICE", eventDays)
        addEvent(2025, Calendar.AUGUST, 2, "Youth Search 2024 (UYFCYM)", eventDays)
        addEvent(2025, Calendar.AUGUST, 5, "P1 Examinations (Upper Years)", eventDays)
        addEvent(2025, Calendar.AUGUST, 23, "Abel Kamustahan 23", eventDays)
        addEvent(2025, Calendar.AUGUST, 15, "Buwan ng Wika 15", eventDays)
        addEvent(2025, Calendar.AUGUST, 27, "P1 Exmination (Freshmen  and SHS gr 11)", eventDays)

        addEvent(2025, Calendar.SEPTEMBER, 6, "Eucharistic Celebration", eventDays)
        addEvent(2025, Calendar.SEPTEMBER, 12, "Mathematics and Science Fest", eventDays)
        addEvent(2025, Calendar.SEPTEMBER, 13, "Social Entrepeneurship", eventDays)
        addEvent(2025, Calendar.SEPTEMBER, 19, "Business Expo and Abel Kamustahan", eventDays)
        addEvent(2025, Calendar.SEPTEMBER, 21, "CITE Fest", eventDays)
        addEvent(2025, Calendar.SEPTEMBER, 20, "PUCU Fest", eventDays)

        addEvent(2025, Calendar.OCTOBER, 1, "P2 Examination (Freshmen)", eventDays)
        addEvent(2025, Calendar.OCTOBER, 3, "Teachers and Staff Appreciation Day", eventDays)
        addEvent(2025, Calendar.OCTOBER, 4, "Rosary Devotion", eventDays)
        addEvent(2025, Calendar.OCTOBER, 9, "League of Leaders", eventDays)
        addEvent(2025, Calendar.OCTOBER, 10, "Criminology Day", eventDays)
        addEvent(2025, Calendar.OCTOBER, 12, "Clean Up Drive", eventDays)
        addEvent(2025, Calendar.OCTOBER, 21, "P3 Examination (Upperclassmen)", eventDays)

        addEvent(2025, Calendar.NOVEMBER, 6, "English Fest", eventDays)
        addEvent(2025, Calendar.NOVEMBER, 8, "Testimonial CEA", eventDays)
        addEvent(2025, Calendar.NOVEMBER, 21, "P3 Examination (Freshmen)", eventDays)
        addEvent(2025, Calendar.NOVEMBER, 31, "SHS Sports Fest", eventDays)

        addEvent(2025, Calendar.DECEMBER, 3, "Lamaparaan", eventDays)
        addEvent(2025, Calendar.DECEMBER, 9, "Ningning Project", eventDays)
        addEvent(2025, Calendar.DECEMBER, 16, "2nd Quarterly Exam (gr 11)", eventDays)


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


        binding.btJuneActivities.setOnClickListener {
            toggleJuneActivities()
        }
        binding.btJulyActivities.setOnClickListener {
            toggleJulyActivities()
        }
        binding.btAugustActivities.setOnClickListener {
            toggleAugustActivities()
        }
        binding.btSeptemberActivities.setOnClickListener {
            toggleSeptemberActivities()
        }
        binding.btOctoberActivities.setOnClickListener {
            toggleOctoberActivities()
        }
        binding.btNovemberActivities.setOnClickListener {
            toggleNovemberActivities()
        }
        binding.btDecemberActivities.setOnClickListener {
            toggleDecemberActivities()
        }
        binding.btJanuaryActivities.setOnClickListener {
            toggleJanuaryActivities()
        }
        binding.btFebruaryActivities.setOnClickListener {
            toggleFebruaryActivities()
        }
        binding.btMarchActivities.setOnClickListener {
            toggleMarchActivities()
        }
        binding.btAprilActivities.setOnClickListener {
            toggleAprilActivities()
        }
        binding.btMayActivities.setOnClickListener {
            toggleMayActivities()
        }
    }
    private fun addEvent(year: Int, month: Int, day: Int, eventName: String, eventDays: ArrayList<EventDay>) {
        val calendar = Calendar.getInstance()
        calendar.set(year, month, day)
        val eventDay = EventDay(calendar, R.drawable.twotone_event_24, ContextCompat.getColor(requireContext(), R.color.red))
        eventDays.add(eventDay)
        events[String.format("%02d-%02d-%d", day, month + 1, year)] = eventName
    }

    private fun toggleJuneActivities() {
        val isVisible = binding.layoutJuneActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvJuneActivities, transition)
        binding.layoutJuneActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleJulyActivities() {
        val isVisible = binding.layoutJulyActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvJulyActivities, transition)
        binding.layoutJulyActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleAugustActivities() {
        val isVisible = binding.layoutAugustActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvAugustActivities, transition)
        binding.layoutAugustActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleSeptemberActivities() {
        val isVisible = binding.layoutSeptemberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvSeptemberActivities, transition)
        binding.layoutSeptemberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleOctoberActivities() {
        val isVisible = binding.layoutOctoberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvOctoberActivities, transition)
        binding.layoutOctoberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleNovemberActivities() {
        val isVisible = binding.layoutNovemberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvNovemberActivities, transition)
        binding.layoutNovemberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleDecemberActivities() {
        val isVisible = binding.layoutDecemberActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvDecemberActivities, transition)
        binding.layoutDecemberActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleJanuaryActivities() {
        val isVisible = binding.layoutJanuaryActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvJanuaryActivities, transition)
        binding.layoutJanuaryActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleFebruaryActivities() {
        val isVisible = binding.layoutFebruaryActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvFebruaryActivities, transition)
        binding.layoutFebruaryActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleMarchActivities() {
        val isVisible = binding.layoutMarchActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvMarchActivities, transition)
        binding.layoutMarchActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }

    private fun toggleAprilActivities() {
        val isVisible = binding.layoutAprilActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvAprilActivities, transition)
        binding.layoutAprilActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }
    private fun toggleMayActivities() {
        val isVisible = binding.layoutMayActivities.visibility == View.VISIBLE
        val transition = AutoTransition()
        TransitionManager.beginDelayedTransition(binding.cvMayActivities, transition)
        binding.layoutMayActivities.visibility = if (isVisible) View.GONE else View.VISIBLE
    }
    override fun onResume() {
        super.onResume()
        (activity as UpangMainActivity).updateActionBarTitle("Events")
    }
}