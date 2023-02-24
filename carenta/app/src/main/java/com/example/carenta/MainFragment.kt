package com.example.carenta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // we display the list of cars
        getcar()
    }

    // the function that brings cars from the data base
    private fun getcar() {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.getCar()
            withContext(Dispatchers.Main){
                if(response.isSuccessful){
                    val car = response.body()
                    if (car != null){
                        val view = requireActivity().findViewById<RecyclerView>(R.id.recycleView)
                        val layoutManager = LinearLayoutManager(requireContext())
                        view?.layoutManager = layoutManager
                        view?.adapter = MyAdapter(requireContext(), car as ArrayList<Car>)
                    }
                    else{
                        Toast.makeText(requireActivity(), "error car is null!", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(requireActivity(), "error try again", Toast.LENGTH_SHORT).show()
                }

            }
        }
    }
}



