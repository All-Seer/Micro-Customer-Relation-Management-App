package com.example.phinmaedapp

import android.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.phinmaedapp.databinding.FragmentUpangScholarshipBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UpangScholarshipFragment : Fragment() {

    private var _binding: FragmentUpangScholarshipBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpangScholarshipBinding.inflate(inflater, container, false)
        setupSpinner()
        return binding.root
    }

    private fun setupSpinner() {
        val options = arrayOf(
            "PHINMA Scholarship(formerly Presidential Scholarship)",
            "Student Assistance Scholarship",
            "Valedictorian & Salutatorian Scholarship",
            "Dean's List Scholarship",
            "With Highest Honor",
            "PHINMA UPang Direct Dependent Scholarship",
            "Hawak Kamay Scholarship",
            "Victory Liner, 5-Star, and Transasia Oil Employee & Direct Dependent Scholarship",
            "The Medical City Employee & Direct Dependent Scholarship",
            "Kapalong @ PHINMA UPang Scholarship",
            "PHINMA UPang Alumni Scholarship",
            "PHINMA UPang Scholarship",
        )
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, options)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.selection.adapter = adapter

        binding.selection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                when (position) {
                    0 -> {
                        binding.descriptions.text = """
                            Select a scholarship to view details
                        """.trimIndent()
                    }
                    1 -> {
                        binding.descriptions.text = """
                            Benefits:

                            - 100% discount on tuition & miscellaneous fees for 4 or 5 years
                            - ₱4,000 monthly allowance for Honors or Top 10 Grade 12 students who passed the qualifying exam.

                            Maintenance:

                            - Service of 2 hrs/day at assigned department
                            - No grade below 2.0
                            - Average must be 1.75 or above
                            - Applicable during summer if required in the curriculum.

                            Requirements:

                            - Scholarship Acceptance letter from Office of the President
                            - Enrollment Registration
                        """.trimIndent()
                    }
                    2 -> {
                        binding.descriptions.text = """
                            Benefits:

                            - 100% off the tuition (21 units only)

                            Eligibility:

                            - Must be in good class standing
                            - Service of 4 hours/day at the assigned department
                            - Must have passing grades
                            - Not applicable during summer.

                            Requirements:

                            - Should pass the examination and interview
                        """.trimIndent()
                    }
                    3 -> {
                        binding.descriptions.text = """
                            Benefits:

                            - 100% (Valedictorian) off the tuition for semester upon entry only.
                            - 50% (Salutatorian) off the tuition for semester upon entry only.

                            Requirements:

                            - Certificate from Principal
                            - Birth Certificate
                            - Enrollment Registration
                        """.trimIndent()
                    }
                    4 -> {
                        binding.descriptions.text = """
                            Benefits:

                            - 100% coverage of tuition fees for the top 10 for the semester following the Dean’s List award
                            - 50% off the tuition fee for the top 11-25 for the semester following the Dean’s List award

                            Requirements:

                            - Grades (to be applied in the Registrar)
                        """.trimIndent()
                    }
                    5 -> {
                        binding.descriptions.text = """
                            Benefits:

                            - 100% off the tuition for semester upon entry only.

                            Requirements:

                            - Certificate from Principal
                            - Birth Certificate
                            - Enrollment Registration
                        """.trimIndent()
                    }
                    6->{
                        binding.descriptions.text="""
                            Benefits:

                            -100% (with duty) or 
                            50% off the tuition fee
                             

                            Eligibility:

                            -Must be in good class standing
                           - No maintaining grade.
                            -Applicable during regular semester and summer.
                             

                            Requirements:

                            -Birth Certificate of the child to be presented to UPang HRD
                            -Certification of Employment from UPang HRD
                            -Enrollment Registration
                             

                            Maintenance:

                            -The student must be a part of the top 11-25 of the Dean’s List who are not Presidential, Gov. Moreno, OJH, 1 of 100, or College scholar.
                            -The student must not have a mark of Incomplete, Dropped or Failed.
                            -The student must have a full load or at least 21 units for irregular students.
                            -The student must not have any disciplinary sanction.
                        """.trimIndent()
                    }
                    7->{
                        binding.descriptions.text = """
                            Benefits:

                            -HK5.5 (₱5,500 per semester)
                            -HK7.5 ( as low as ₱7,500 per semester)
                            -HK10 ( as low as ₱10,000 per semester)
                            -HK12.5 (as low as ₱12,000 per semester)
                            ** Books and uniforms not included

                             

                            Requirements:

                            -Must pass exam given at the Marketing Dept.
                             --No maintaining grade
                        """.trimIndent()
                    }
                    8->{
                        binding.descriptions.text = """
                            Benefits:

                            -25% off the tuition fee
                            -Renewable as long as the parent is an employee of the above
                             

                            Eligibility:

                            -Must be in good class standing
                            -No maintaining grade
                            -Not applicable during summer.
                             

                            Requirements:

                            -Certificates of Employment of Parents
                            -Employment ID
                            -Birth Certificates of Child
                        """.trimIndent()
                    }
                    9->{
                        binding.descriptions.text="""
                            Benefits:

                            -50% off the tuition fees
                            -Renewable as long as the parent is still connected in the TMC
                             

                            Eligibility:

                            -Must be in good class standing
                            -No maintaining grade
                            -Applicable during regular semester and summer
                             

                            Requirements:

                            -Certificates of Employment from TMCHR
                            -Employment ID
                            -Birth Certificates of Child
                            -Enrollment Registration
                        """.trimIndent()
                    }
                    10 ->{
                        binding.descriptions.text = """
                            Benefits:

                            -10% off the tuition for 2 siblings, plus 5% to each additional sibling enrolled in the University (basic education or college)
                             

                            Eligibility:

                            -No maintaining grades
                            -Applicable during summer if required in the curriculum
                             

                            Requirements:

                            -Birth Certificates of each sibling
                            -Certificate of Matriculation
                        """.trimIndent()
                    }
                    11->{
                        binding.descriptions.text = """
                            Benefits:

                            -10% off the tuition fee for graduates of the University (including Basic Education) and their direct dependents
                             

                            Eligibility:

                            -Must be in good class standing
                            -No maintaining grade
                            -Not applicable during summer
                             

                            Requirements:

                            -Alumni card from Alumni Office / Parent’s Diploma / OTR
                            -Birth Certificate of the Child
                            -Enrollment Registration
                        """.trimIndent()
                    }
                }
            }

            override fun onNothingSelected(parentView: AdapterView<*>) {
                binding.descriptions.text = ""
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpangScholarshipFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
