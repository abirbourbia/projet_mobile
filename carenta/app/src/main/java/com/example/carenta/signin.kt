package com.example.carenta

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.edit
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.carenta.databinding.ActivitySigninBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class signin : AppCompatActivity() {
    lateinit var binding : ActivitySigninBinding
    lateinit var namefull : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // button to move to the signup
       binding.signuplink.setOnClickListener{
            startActivity(Intent(this,signup::class.java))
            finish()
        }
        //button to exit
        binding.goback.setOnClickListener{
            finish()
        }


        // processing the login :
        // verify if the user is connected
        val pref = getSharedPreferences("fileName", Context.MODE_PRIVATE)
        if(pref.getBoolean("connected",false)) {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Login onClick
        binding.signin.setOnClickListener {
            val phonenumber = binding.Phone.text.toString()
            val password = binding.Password.text.toString()
            login(phonenumber,password)
        }
    }
    private fun login(phonenumber: String,password: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val response =  RetrofitService.endpoint.login(LoginCreds(phonenumber,password))
            withContext(Dispatchers.Main) {
                if(response.isSuccessful) {
                   val user = response.body()
                    if(user!=null) {
                        val pref = getSharedPreferences("fileName", Context.MODE_PRIVATE)
                        pref.edit {
                            putInt("idUser",user.id)
                            putString("userName", user.fullname)
                            putString("phoneNumber",user.phonenumber)
                            putBoolean("connected",true)
                        }
                        println("username isssssss"+pref.getInt("idUser",0))
                        val intent = Intent(this@signin,MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                    else {
                        Toast.makeText(this@signin,"Recheck Phone Number or Password ", Toast.LENGTH_SHORT).show()
                    }
                }
                else {
                    Toast.makeText(this@signin,response.toString(), Toast.LENGTH_LONG).show()
                }
            }

        }
    }
}