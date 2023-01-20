package com.example.carenta

import android.content.Context
import android.graphics.Color
import android.graphics.Color.green
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carenta.databinding.CarLayoutBinding

class MyAdapter(val context: Context,var data:List<Car>):RecyclerView.Adapter<MyAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(CarLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            //image.setImageResource(data[position].image)
            // beh njibou l'image mn la bdd
            Glide.with(context).load(url+data[position].image).into(image)
            model.text = data[position].model
            //moteur.text = data[position].motor
            tarif.text = data[position].tarif+"0DA/H"
            if(data[position].disponibility=="1"){
                disponibility.text = "Available"
                disponibility.setTextColor(Color.GREEN)
            } else{
                disponibility.text = "Not Available"
                disponibility.setTextColor(Color.RED)
            }

            //mark.setImageResource(data[position].mark)
        }

        val card = holder.binding.card
        card.setOnClickListener {
          //  it.findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
            var bundle = bundleOf("car" to data[position])
            it.findNavController().navigate(R.id.action_mainFragment_to_detailFragment,bundle)
        }
    }

    class MyViewHolder(val binding: CarLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

}