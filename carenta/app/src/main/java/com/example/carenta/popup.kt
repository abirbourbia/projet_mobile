package com.example.carenta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.carenta.databinding.FragmentDetail2Binding
import com.example.carenta.databinding.FragmentDetailBinding
import com.example.carenta.databinding.FragmentPopupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.sql.Types.NULL
import java.util.*

class popup : DialogFragment() {
    lateinit var binding: FragmentPopupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPopupBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val res = arguments?.getSerializable("res") as reservation
        println("ouiiiiiiiiiiiiiiii"+res)
        binding.pincodeid.text = res.pincode
        binding.idcancel.setOnClickListener {
            Toast.makeText(requireContext(),"action canceled",Toast.LENGTH_LONG).show()
            dismiss()
        }
        binding.idconfirm.setOnClickListener {
            //addReservation(res)
            Toast.makeText(requireContext(),"action done",Toast.LENGTH_LONG).show()
            dismiss()
        }

    }

    private fun addReservation(body: reservation) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.addRES(body)
            withContext(Dispatchers.Main) {
                binding.idconfirm.isEnabled = true
                if (response.isSuccessful) {
                    Toast.makeText(requireActivity(),"Operation accomplished",Toast.LENGTH_LONG).show()
                    val intent = Intent(requireActivity(), reservations::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                }
                else {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}