package com.example.carenta

import android.Manifest
import android.app.ProgressDialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Context
import android.content.pm.PackageManager
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import com.example.carenta.bluetouth.Companion.EXTRA_ADDRESS
import com.example.carenta.databinding.FragmentBluetouthDetailsBinding
import java.io.IOException
import java.util.*
import java.util.zip.Inflater

class bluetouthDetails : Fragment() {

    lateinit var binding : FragmentBluetouthDetailsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =  FragmentBluetouthDetailsBinding.inflate(inflater,container, false)
        val view = binding.root
        return view
    }
}