package com.example.carenta

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import java.text.SimpleDateFormat
import java.util.*
import androidx.core.os.bundleOf as bundleOf1


class signup1 : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup1, container, false)
        val btnNext = view.findViewById<Button>(R.id.next1)
        val userName = view.findViewById<EditText>(R.id.TextPersonName)
        val phoneNum = view.findViewById<EditText>(R.id.editTextPhone)
        val date = view.findViewById<EditText>(R.id.editTextDate)

        // buttons to go back to the login in activity
        view.findViewById<TextView>(R.id.signinlink).setOnClickListener {
            startActivity(Intent(requireActivity(), signin::class.java))
            requireActivity().finish()
        }
        view.findViewById<ImageView>(R.id.goback).setOnClickListener {
            startActivity(Intent(requireActivity(), signin::class.java))
            requireActivity().finish()
        }

        // button to move to the next fragment
        btnNext.setOnClickListener {
            // checking if the user full filled all the required information
            if (TextUtils.isEmpty(userName.getText()) || TextUtils.isEmpty(phoneNum.getText()) || TextUtils.isEmpty(date.getText())) {
                if (TextUtils.isEmpty(userName.getText())) {
                    userName.setError("First name is required!")
                }
                if (TextUtils.isEmpty(phoneNum.getText())) {
                    phoneNum.setError("Phone Number is Required!")
                }
                if (TextUtils.isEmpty(date.getText())) {
                    date.setError("Birth Date is Required!")
                }
                Toast.makeText(requireActivity(), "All Information Are Required", Toast.LENGTH_LONG)
                    .show()

            } else {
                // we send data to the next fragment
                var bundle = bundleOf1("userName" to userName.text.toString(), "date" to date.text.toString(), "phoneNumber" to phoneNum.text.toString())
                requireActivity().findNavController(R.id.fragmentContainerView).navigate(R.id.action_signup1_to_signup2, bundle) }
        }

        // Condition on the date
        date.inputType = InputType.TYPE_NULL
        date.setOnClickListener {
            val cal = Calendar.getInstance()
            val picker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                date.setText(SimpleDateFormat("dd/MM/yyyy").format(cal.time))
            }
            // the date dialog appearing
            DatePickerDialog(
                requireContext(), picker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()

        }
        return view
    }

}