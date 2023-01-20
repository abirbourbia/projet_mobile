package com.example.carenta

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.carenta.databinding.TrajetLayoutBinding

class TrajetAdapter(val context: Context, var data: List<Trajet>):RecyclerView.Adapter<TrajetAdapter.MyViewHolder2>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder2 {
        return MyViewHolder2(TrajetLayoutBinding.inflate(LayoutInflater.from(context), parent, false))


    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder2, position: Int) {
      holder.binding.apply {
          depart.text = data[position].date
          arrive.text = data[position].hour_debut
        }

       val card = holder.binding.card
        card.setOnClickListener {


          //  it.findNavController().navigate(R.id.action_mainFragment_to_detailFragment)
          //  var bundle = bundleOf("position" to position)
           // it.findNavController().navigate(R.id.action_mainFragment_to_detailFragment,bundle)
       }




    }

    class MyViewHolder2(val binding: TrajetLayoutBinding) : RecyclerView.ViewHolder(binding.root) {


    }

}