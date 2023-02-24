package com.example.carenta

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.carenta.databinding.TrajetLayoutBinding
import java.text.SimpleDateFormat
import java.util.*

class MonAdapter(val context: Context, var data:List<reservation>, var cars:List<Car>): RecyclerView.Adapter<MonAdapter.MyViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(TrajetLayoutBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        // Here I'm tryning the display the reservation's information
        holder.binding.apply {
            destination.text = "end: "+data[position].destination
            source.text = "start: "+data[position].source
            dateDebut.text = data[position].dateDebut
            dateFin.text = data[position].dateFin
            carname.text = cars[position].model
            val dade = SimpleDateFormat("dd/MM/yyyy")
            val dateD: Date = dade.parse(data[position].dateDebut)
            val dafi = SimpleDateFormat("dd/MM/yyyy")
            val dateF: Date = dafi.parse(data[position].dateFin)
            val diff: Long = (dateF.getTime() - dateD.getTime()) / 86400000
            tripduration.text = diff.toString()+" day"
            tripprice.text = cars[position].tarif+"0DA/Day"
            total.text = (cars[position].tarif.toInt() * diff).toString() + "0DA"
            Glide.with(context).load(url+cars[position].image).into(logoCar)

            // sending the information of the selected reservation
            var bundle = Bundle().apply{putSerializable("car",cars[position])}
            card1.setOnClickListener {
                // once he clicks on pay Now the popup will appear
                if(cars[position].disponibility=="0")
                {
                    val showPopUp1 = freecar()
                    showPopUp1.arguments = bundle
                    showPopUp1.show(
                        (context as AppCompatActivity).supportFragmentManager,
                        "showPopUp1"
                    )
                }
                else {
                    Toast.makeText(context,"Car already free !",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    class MyViewHolder(val binding: TrajetLayoutBinding) : RecyclerView.ViewHolder(binding.root) {}

}
