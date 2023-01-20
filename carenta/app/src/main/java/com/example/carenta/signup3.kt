package com.example.carenta

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.telephony.SmsManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.concurrent.fixedRateTimer


class signup3 : Fragment() {
    private lateinit var btn_upload : ImageButton
    private lateinit var img : ImageView
    companion object{
        val IMAGE_REQUEST_CODE = 100
    }
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_signup3, container, false)

        // the buttons to go back to the previous fragment
        val btn = view.findViewById<Button>(R.id.back2)
        btn.setOnClickListener {
            findNavController().navigate(R.id.action_signup3_to_signup2)
        }
        view.findViewById<ImageView>(R.id.goback3).setOnClickListener{
            findNavController().navigate(R.id.action_signup3_to_signup2)
        }

        // button to go back to the sign in activity
        view.findViewById<TextView>(R.id.signinlink).setOnClickListener{
            startActivity(Intent(requireActivity(), signin::class.java))
            requireActivity().finish()
        }
        var x = rand(1000,9999)
        var id = 2
        var pic = Unit
        // code to upload the driving license from gallery
        btn_upload = view.findViewById<ImageButton>(R.id.upload)
        img = view.findViewById<ImageView>(R.id.drivinglicenceimg)
        btn_upload.setOnClickListener {
           pic = pickImageGallery()
        }
        val userName = arguments?.getString("userName")
        val phoneNum = arguments?.getString("phoneNumber")
        val dateb = arguments?.getString("date")
        val creditc = arguments?.getString("creditcard")
        val expDate= arguments?.getString("expDate")
        val userdb = user(id,userName,phoneNum,x.toString(),dateb,creditc,expDate,"drive.png")
        //Toast.makeText(requireActivity(),"${userdb.password}",Toast.LENGTH_LONG).show()

        view.findViewById<Button>(R.id.submit).setOnClickListener {
                try {
                    val smsManager: SmsManager
                    if (Build.VERSION.SDK_INT>=23) {
                        smsManager = requireActivity().getSystemService(SmsManager::class.java)
                    }
                    else{
                        smsManager = SmsManager.getDefault()
                    }
                    smsManager.sendTextMessage(phoneNum, "0770523120", "your password is $x", null, null)
                    Toast.makeText(requireActivity(), "Message Sent", Toast.LENGTH_LONG).show()

                } catch (e: Exception) {
                    Toast.makeText(requireActivity(), "Please enter all the data.."+e.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            }
        return view
    }
    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
         startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK){
            img.setImageURI(data?.data)
        }
    }

    // override the random function where we can generate random number
    val random = Random()
    fun rand(from: Int, to: Int) : Int {
        return random.nextInt(to - from) + from
    }

}