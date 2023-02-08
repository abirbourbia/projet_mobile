package com.example.carenta

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings.Global.putInt
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.sql.Types.NULL
import java.util.*


class signup3 : Fragment() {
    private lateinit var activityResultLauncher: ActivityResultLauncher<Intent>
    private lateinit var activityResultLauncher1: ActivityResultLauncher<Intent>
    private lateinit var btn_upload_gallery : ImageButton
    private lateinit var btn_upload_camera: ImageButton
    private lateinit var btn_submit: Button
    private lateinit var image: ImageView
    lateinit var imageBitmap: Bitmap
    val requestCode = 400

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
        // code to upload the driving license from gallery
        btn_upload_gallery = view.findViewById<ImageButton>(R.id.uploadGallery)
        btn_upload_camera = view.findViewById<ImageButton>(R.id.uploeadCamera)
        image = view.findViewById<ImageView>(R.id.driving)
        btn_submit = view.findViewById<Button>(R.id.submit)
        val userName = arguments?.getString("userName")
        val phoneNum = arguments?.getString("phoneNumber")
        val dateb = arguments?.getString("date")
        val creditc = arguments?.getString("creditcard")
        val expDate= arguments?.getString("expDate")
        val userdb = user(NULL,userName,phoneNum,x.toString(),dateb,creditc,expDate)

        // in case you wanted to capture the image from camera
       activityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val intent = result.data
            if (result.resultCode == AppCompatActivity.RESULT_OK && intent != null) {
                imageBitmap = intent.extras?.get("data") as Bitmap
                image.setImageBitmap(imageBitmap)
            }
        }
        btn_upload_camera.setOnClickListener {
            //pic = pickImageGallery()
            if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED)  {
                openCameraIntent()
            }
            else {
                checkPermission()
            }
        }

        // in case you wanted to bring the pic from gallery
        activityResultLauncher1 = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val intent = result.data
            if (result.resultCode == AppCompatActivity.RESULT_OK && intent != null) {
                val selectedImageUri = intent.getData()
                imageBitmap = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    val source = ImageDecoder.createSource(requireActivity().contentResolver, selectedImageUri!!)
                    ImageDecoder.decodeBitmap(source)
                } else {
                    @Suppress("DEPRECATION")
                    MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, selectedImageUri)
                }
                image.setImageBitmap(imageBitmap)
            }
        }
        // clicking on this botton allows us the pick an image from the camera
        btn_upload_gallery.setOnClickListener {
            imageChooser()
        }

        // clicking on this botton will send the data, the user's related information plus the pic's path and upload it locally in public folder.
        btn_submit.setOnClickListener {
            btn_submit.isEnabled  = false
            // convert Bitmap to File
            val filesDir = requireActivity().getFilesDir()
            val file = File(filesDir, "image" + ".png")
            val bos = ByteArrayOutputStream()
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 0, bos)
            val bitmapdata = bos.toByteArray()
            val fos = FileOutputStream(file)
            fos.write(bitmapdata)
            fos.flush()
            fos.close()
            val reqFile = RequestBody.create(MediaType.parse("image/*"), file)
            val image = MultipartBody.Part.createFormData("image", file.getName(), reqFile)
            val userBody =  MultipartBody.Part.createFormData("user", Gson().toJson(userdb))
            addUser(image,userBody)
        }
        return view
    }

    private fun addUser(body: MultipartBody.Part, user:MultipartBody.Part) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.addPDV(body, user)
            withContext(Dispatchers.Main) {
                btn_submit.isEnabled = true
                if (response.isSuccessful) {
                    val intent = Intent(requireActivity(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                    }
                 else {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    // Take a picture launching camera 1
    fun openCameraIntent() {
        val pictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        activityResultLauncher.launch(pictureIntent)
    }
    // Request permission
    private fun checkPermission() {
        val perms = arrayOf(Manifest.permission.CAMERA)
        ActivityCompat.requestPermissions(requireActivity(),perms, requestCode)

    }
    override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(permsRequestCode, permissions, grantResults)
        if (permsRequestCode==requestCode && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            openCameraIntent()
        }
    }

    // Function to choose a pic from the gallery
    fun imageChooser() {
        val intent = Intent()
        intent.setType("image/*")
        intent.setAction(Intent.ACTION_GET_CONTENT)
        activityResultLauncher1.launch(intent)
    }
    // override the random function where we can generate random number
    val random = Random()
    fun rand(from: Int, to: Int): Int {
        return random.nextInt(to - from) + from
    }


    // My picture chooser function, but all it does is bringing the URI of the image aka it's path instead of the whole pic.
        /* private fun pickImageGallery() {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, IMAGE_REQUEST_CODE)
        }

        override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
            super.onActivityResult(requestCode, resultCode, data)
            if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
                img.setImageURI(data?.data)
               // uri= img.setImageURI(data?.data)
            }
        } */

         */

    }