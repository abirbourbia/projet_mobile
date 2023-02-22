package com.example.carenta

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.carenta.databinding.TrajetLayoutBinding

class MonAdapter(val context: Context, var data:List<reservation>): RecyclerView.Adapter<MonAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(TrajetLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.binding.apply {
            destination.text = data[position].destination
            source.text = data[position].source
            dateDebut.text = data[position].dateDebut
            dateFin.text = data[position].dateFin
            carname.text = "ferari"
            tripduration.text = "2 days"
            tripprice.text = "2000DA"

        }
    }

    class MyViewHolder(val binding: TrajetLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

}
