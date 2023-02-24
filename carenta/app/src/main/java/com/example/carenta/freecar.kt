package com.example.carenta

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.carenta.databinding.FragmentFreecarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class freecar : DialogFragment() {
    lateinit var binding: FragmentFreecarBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFreecarBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val car = arguments?.getSerializable("car") as Car

            binding.idYes.setOnClickListener {
                updatecar("1",car.id)
                Toast.makeText(requireActivity(),"Car Freed!",Toast.LENGTH_SHORT).show()
                dismiss()
            }
            binding.idNo.setOnClickListener {
                Toast.makeText(requireActivity(),"No modification applied!",Toast.LENGTH_SHORT).show()
                dismiss()
            }
    }

    // the car's disponibility is automatically set to "not available"
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