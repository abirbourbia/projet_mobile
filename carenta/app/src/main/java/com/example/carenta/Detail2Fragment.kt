package com.example.carenta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.carenta.databinding.FragmentDetail2Binding
import com.example.carenta.databinding.FragmentDetailBinding

class Detail2Fragment : Fragment() {
    lateinit var binding: FragmentDetail2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        // return inflater.inflate(R.layout.fragment_detail2, container, false)
        binding = FragmentDetail2Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val car = arguments?.getSerializable("car") as Car

        if(car!=null) {
            binding.model.text = car.model
            Glide.with(requireActivity()).load(url+car.marque).into(binding.mark)
            Glide.with(requireActivity()).load(url+car.image).into(binding.image)
        }
    }

}