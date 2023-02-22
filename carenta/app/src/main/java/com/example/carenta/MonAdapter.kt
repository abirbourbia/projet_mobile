package com.example.carenta

import android.content.Context
import android.provider.CalendarContract.EventDays
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carenta.databinding.TrajetLayoutBinding
import java.text.SimpleDateFormat
import java.time.Period
import java.util.*

class MonAdapter(val context: Context, var data:List<reservation>, var cars:List<Car>): RecyclerView.Adapter<MonAdapter.MyViewHolder>() {



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
            carname.text = cars[position].model
            val dade = SimpleDateFormat("dd/MM/yyyy")
            val dateD: Date = dade.parse(data[position].dateDebut)
            val dafi = SimpleDateFormat("dd/MM/yyyy")
            val dateF: Date = dafi.parse(data[position].dateFin)
            val diff: Long = (dateF.getTime() - dateD.getTime()) / 86400000
            tripduration.text = diff.toString()+" day"
            tripprice.text = cars[position].tarif+"0DA"
            Glide.with(context).load(url+cars[position].image).into(logoCar)
        }
    }

    class MyViewHolder(val binding: TrajetLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

}
