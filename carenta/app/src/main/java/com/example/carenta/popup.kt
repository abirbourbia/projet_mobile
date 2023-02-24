package com.example.carenta

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.carenta.databinding.FragmentPopupBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
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
        binding.pincodeid.text = res.pincode
        binding.idcancel.setOnClickListener {
            Toast.makeText(requireContext(), "action canceled", Toast.LENGTH_LONG).show()
            dismiss()
        }
        binding.idconfirm.setOnClickListener {
            addReservation(res)
            updatecar("0", res.id_car)
            Toast.makeText(requireActivity(), "Operation accomplished", Toast.LENGTH_LONG).show()
            dismiss()
        }

    }

    private fun addReservation(body: reservation) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.addRES(body)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    // done
                } else {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updatecar(disponibility: String, id: Int?) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.updatecar(ModifyCreds(disponibility, id))
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val data = response.body()
                    if (data != null && data.affectedRows == 1) {
                        // the car is now not available
                    } else {
                        Toast.makeText(requireActivity(), "Error updating car availability", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}