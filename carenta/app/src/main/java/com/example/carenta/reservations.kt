package com.example.carenta

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.carenta.databinding.ReservationsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class reservations : Fragment() {
    lateinit var binding : ReservationsBinding
    private val viewModel: CarModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ReservationsBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Here we're retrieving the user's information
        val pref = requireActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE)
        binding.username.text = pref.getString("userName","error")
        getReservation()
    }

    // Getting the reservations
    private fun getReservation() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.getReservation()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val listReservation = mutableListOf<reservation>()
                    val listCar = mutableListOf<Car>()
                    val pref = requireActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE)
                    val reservation = response.body()
                    if (reservation != null) {
                        viewModel.carList.observe(requireActivity()) { carList ->
                            // i verify if the reservation belongs to the logged user
                            for (res in reservation) {
                                for (car in carList) {
                                    if (car.id == res.id_car && res.id_user == pref.getInt("idUser", 0))
                                    {
                                       listReservation.add(res)
                                        listCar.add(car)
                                    }
                                }
                            }
                        }
                        val view = requireActivity().findViewById<RecyclerView>(R.id.recycleView1)
                        val layoutManager = LinearLayoutManager(requireContext())
                        view.layoutManager = layoutManager
                        view.adapter = MonAdapter(requireContext(), listReservation as ArrayList<reservation>,listCar)
                    } else {
                        Toast.makeText(
                            requireActivity(),
                            "error reservation is null!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(requireActivity(), "error try again", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}