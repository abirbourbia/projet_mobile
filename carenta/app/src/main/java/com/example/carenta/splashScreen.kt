package com.example.carenta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.carenta.databinding.ActivitySplashScreenBinding


class splashScreen : AppCompatActivity() {
    lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.getstarted.setOnClickListener {
            startActivity(Intent(this, signin::class.java))
            finish()
        }

    }

}