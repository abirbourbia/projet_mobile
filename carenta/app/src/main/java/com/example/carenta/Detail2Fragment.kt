package com.example.carenta

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import com.bumptech.glide.Glide
import com.example.carenta.databinding.FragmentDetail2Binding
import com.example.carenta.databinding.FragmentDetailBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MultipartBody
import java.text.SimpleDateFormat
import java.util.*

class Detail2Fragment : Fragment() {
    lateinit var binding: FragmentDetail2Binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetail2Binding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val car = arguments?.getSerializable("car") as Car
        val idCar = car.id
        if (car != null) {
            binding.model.text = car.model
            Glide.with(requireActivity()).load(url + car.marque).into(binding.mark)
            Glide.with(requireActivity()).load(url + car.image).into(binding.image)
        }

        // call button
        binding.phonecard.setOnClickListener{
            call(view)
        }
        // start date text
        binding.startDate.inputType = InputType.TYPE_NULL
        binding.startDate.setOnClickListener {
            val cal = Calendar.getInstance()
            val picker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, month)
                cal.set(Calendar.DAY_OF_MONTH, day)
                binding.startDate.setText(SimpleDateFormat("dd/MM/yyyy").format(cal.time))
            }
            DatePickerDialog(
                requireContext(),
                picker,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(
                    Calendar.DAY_OF_MONTH
                )
            ).show()

            // end date text
            binding.endDate.inputType = InputType.TYPE_NULL
            binding.endDate.setOnClickListener {
                val cal = Calendar.getInstance()
                val picker = DatePickerDialog.OnDateSetListener { datePicker, year, month, day ->
                    cal.set(Calendar.YEAR, year)
                    cal.set(Calendar.MONTH, month)
                    cal.set(Calendar.DAY_OF_MONTH, day)
                    binding.endDate.setText(SimpleDateFormat("dd/MM/yyyy").format(cal.time))
                }
                DatePickerDialog(
                    requireContext(),
                    picker,
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(
                        Calendar.DAY_OF_MONTH
                    )
                ).show()
            }


            // the bottun that shows the confirmation popup plus the pin code
            binding.book.setOnClickListener {
                if (TextUtils.isEmpty(binding.startDate.getText()) || TextUtils.isEmpty(binding.endDate.getText())
                    || TextUtils.isEmpty(binding.startAdr.getText()) || TextUtils.isEmpty(binding.pickAdr.getText())
                ) {
                    Toast.makeText(requireActivity(), "You have to fill all 4 cases", Toast.LENGTH_LONG).show()
                } else {
                    /* var bundle = bundleOf(
                        "userid" to 1,
                        "carid" to car.id,
                        "dated" to binding.startDate.text.toString(),
                        "datef" to binding.endDate.text.toString(),
                        "adrd" to binding.startAdr.text.toString(),
                        "adrf" to binding.pickAdr.text.toString()
                    )
                    requireActivity().findNavController(R.id.fragmentContainerView).
                    navigate(R.id.action_detail2Fragment_to_popup, bundle) }*/
                    val showPopUp = popup()
                    showPopUp.show(
                        (requireActivity() as AppCompatActivity).supportFragmentManager,
                        "showPopUp"
                    )
                }
            }
        }

    }

        fun call(view: View) {
            val dialIntent = Intent(Intent.ACTION_DIAL)
            dialIntent.data = Uri.parse("tel:" + "0783057340")
            startActivity(dialIntent)
        }
}

