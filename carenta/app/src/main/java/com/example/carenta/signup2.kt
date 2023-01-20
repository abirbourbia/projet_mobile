package com.example.carenta

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*


class signup2 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup2, container, false)

        // buttons to navigate back and next between fragments
        val btnNext = view.findViewById<Button>(R.id.next2)
        val btnback = view.findViewById<Button>(R.id.back1)
        val creditcard = view.findViewById<EditText>(R.id.editTextNumber)
        val date = view.findViewById<EditText>(R.id.editTextDate2)
        val userName = arguments?.getString("userName")
        val phoneNum = arguments?.getString("phoneNumber")
        val dateb = arguments?.getString("date")
        btnNext.setOnClickListener {
            if (TextUtils.isEmpty(creditcard.getText()) || TextUtils.isEmpty(date.getText())) {
                Toast.makeText(requireActivity(), "All Information Are Required", Toast.LENGTH_LONG)
                    .show()
                if (TextUtils.isEmpty(creditcard.getText())) {
                    creditcard.setError("Phone Number is Required!")
                }
                if (TextUtils.isEmpty(date.getText())) {
                    date.setError("Birth Date is Required!")
                }
            } else if (creditcard.text.toString().length != 16) {
                creditcard.setError("Enter a valid Number")
            } else if (date.text.toString().length != 5) {
                Toast.makeText(
                    requireActivity(),
                    "Date Format mm/yy " + LocalDate.now()
                        .format(DateTimeFormatter.ofPattern("MM/yy")).toString(),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                var bundle = bundleOf(
                    "userName" to userName,
                    "date" to dateb,
                    "phoneNumber" to phoneNum,
                    "creditcard" to creditcard.text.toString(),
                    "expDate" to date.text.toString()
                )
                requireActivity().findNavController(R.id.fragmentContainerView)
                    .navigate(R.id.action_signup2_to_signup3, bundle)
            }
        }

        // Condition on the date
        date.inputType = InputType.TYPE_NULL
        date.setOnClickListener {
            val cal = Calendar.getInstance()
            val picker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                date.setText(SimpleDateFormat("MM/yy").format(cal.time))
            }

            DatePickerDialog(
                requireContext(), picker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()
        }
        btnback.setOnClickListener {
            findNavController().navigate(R.id.action_signup2_to_signup1)
        }
        view.findViewById<ImageView>(R.id.goback2).setOnClickListener{
            findNavController().navigate(R.id.action_signup2_to_signup1)
        }
        // button to go pack to the sign in page
        view.findViewById<TextView>(R.id.signinlink).setOnClickListener{
            startActivity(Intent(requireActivity(), signin::class.java))
            requireActivity().finish()

    }
        return view
}
}