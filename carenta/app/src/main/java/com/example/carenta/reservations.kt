package com.example.carenta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class reservations : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.reservations, container, false)
        //val pref = requireActivity().getSharedPreferences("user", AppCompatActivity.MODE_PRIVATE)
        //pref.
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view?.findViewById<TextView>(R.id.username)?.text = "Halle Remeche"
        Toast.makeText(requireActivity(),"cc hehe",Toast.LENGTH_SHORT).show()
        getReservation()
    }

    private fun getReservation() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.getReservation()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val reservation = response.body()
                    if (reservation != null) {
                        val view = requireActivity().findViewById<RecyclerView>(R.id.recycleView1)
                        val layoutManager = LinearLayoutManager(requireContext())
                        view.layoutManager = layoutManager
                        view.adapter = MonAdapter(requireContext(), reservation as ArrayList<reservation>)
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