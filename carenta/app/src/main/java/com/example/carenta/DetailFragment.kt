package com.example.carenta

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.text.InputType.TYPE_NULL
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.carenta.databinding.FragmentDetailBinding
import java.text.SimpleDateFormat
import java.util.*

class DetailFragment : Fragment() {
   lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val car = arguments?.getSerializable("car") as Car

        if(car!=null) {
            binding.model.text = car.model
            binding.tarif.text = car.tarif+"0DA/H"
            Glide.with(requireActivity()).load(url+car.marque).into(binding.mark)
            Glide.with(requireActivity()).load(url+car.image).into(binding.image)
        }
    }
}




        //val carModel = ViewModelProvider(requireActivity()).get(CarModel::class.java)

        //binding = FragmentDetailBinding.inflate(layoutInflater)
     //   val position = arguments?.getInt("position")
       // if (position != null) {
           // val car = carModel.loadData().get(position)
         //   val toast = Toast.makeText(requireContext(),"${car.model}", Toast.LENGTH_SHORT)
           // toast.show()
           // binding.image.setImageResource(car.image)
            //binding.mark.setImageResource(car.mark)
          //  binding.model.text = car.model
            //binding.moteur.text = car.motor
       /*     binding.tarif.text = car.tarif
            binding.disponibility.text = car.disponibility
        }


        val editTextDate = binding.editTextDate as EditText
        val editTextTime = binding.editTextTime as EditText
        // read only
        editTextDate.inputType = TYPE_NULL
        editTextTime.inputType = TYPE_NULL

        // Date
        editTextDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val picker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                editTextDate.setText(SimpleDateFormat("dd/MM/yyyy").format(cal.time))
            }

            DatePickerDialog(
                requireContext(), picker, cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()

        }

        // TIme
        editTextTime.setOnClickListener {
            // Time
            val cal = Calendar.getInstance()
            val picker = TimePickerDialog.OnTimeSetListener { timePicker, hour, time ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, time)
                editTextTime.setText(SimpleDateFormat("HH:mm").format(cal.time))
            }

            TimePickerDialog(
                requireContext(),
                picker,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()

        }

    }


} */