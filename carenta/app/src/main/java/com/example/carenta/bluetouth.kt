package com.example.carenta

import android.Manifest
import android.annotation.SuppressLint
import android.app.Dialog
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.carenta.databinding.FragmentBluetouthBinding
import com.psp.bluetoothlibrary.Bluetooth
import com.psp.bluetoothlibrary.BluetoothListener
import com.psp.bluetoothlibrary.Connection
import java.util.*


class bluetouth : Fragment() {

    private val tag1 = "bluetooth"
    private lateinit var connection: Connection
    lateinit var binding : FragmentBluetouthBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBluetouthBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        val view = binding.root
        val btn = binding.selectDeviceRefresh

        // bluetouth permissions
        val  bluetooth = Bluetooth(this@bluetouth.context)
        bluetooth.turnOnWithPermission(requireActivity())
        logMsg("initialize connection object")
        connection =  Connection(requireActivity())
        // set UUID ( optional )
        connection.setUUID(UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"))

        getDeviceAddressAndConnect()
        btn.setOnClickListener{

            val bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            if (bluetoothAdapter == null) {
                // Bluetooth is not supported on this device
            } else if (!bluetoothAdapter.isEnabled) {
                // Bluetooth is not enabled. You can prompt the user to enable it:
                val  bluetooth =   Bluetooth(requireActivity())
                bluetooth.turnOnWithPermission(requireActivity())
            } else {
                // Bluetooth is enabled
                getDeviceAddressAndConnect()
            }

        }
        val pin = "0000"
        if (connection.send(pin)) {
            logMsg("[TX] $pin")
            Toast.makeText(requireActivity(), "Pin sent successfully", Toast.LENGTH_SHORT).show()
        } else {
            logMsg("[TX] $pin")
            Toast.makeText(requireActivity(), "Failed", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onStart() {
        super.onStart()
        if (connection.isConnected) {
            logMsg("initialize receive listener")
            connection.setOnReceiveListener(receiveListener)
        }
        logMsg("onStart")
    }

    override fun onDestroy() {
        super.onDestroy()
        logMsg("onDestroy")
        disconnect()
    }
    private val receiveListener =
        BluetoothListener.onReceiveListener { receivedData ->
            logMsg("[RX] $receivedData")

        }

    private fun disconnect() {

        connection.disconnect()
        logMsg("Manuel disconnect")

    }

    private fun getDeviceAddressAndConnect() {
        val listPaired: ArrayList<String> = ArrayList()
        getPairedDevices(listPaired) // get paired devices
        for (i in 0..listPaired.size-1 ){
            val device: List<String> = listPaired[i].split("\n")
            if (device[0]=="App"){
                if (connection.connect(device[1], true, connectionListener, receiveListener)) {
                    Toast.makeText(requireActivity(), "Start connection process", Toast.LENGTH_SHORT).show()
                    logMsg("Start connection process")
                } else {
                    Toast.makeText(requireActivity(), "Start connection process failed", Toast.LENGTH_SHORT).show()
                    logMsg("Start connection process failed")
                }
            }
            else{
                Toast.makeText(requireActivity(), "car not found", Toast.LENGTH_SHORT).show()
            }
        }
    }


    @SuppressLint("MissingPermission")
    private fun getPairedDevices(list: ArrayList<String>) {
        // initialize bluetooth object
        val bluetooth = Bluetooth(requireActivity())
        val deviceList = bluetooth.pairedDevices
        if (deviceList.size  > 0) {
            for (device in deviceList) {
                list.add(
                    """
                    ${device.name}
                    ${device.address}
                    """.trimIndent()
                )
                Log.d(tag1, "Paired device is " + device.name)
            }
        }
    }

    private val connectionListener: BluetoothListener.onConnectionListener = object :
        BluetoothListener.onConnectionListener {
        override fun onConnectionStateChanged(socket: BluetoothSocket?, state: Int) {
            when (state) {
                Connection.CONNECTING -> {
                    logMsg("Connecting...")
                }
                Connection.CONNECTED -> {
                    logMsg("Connected")
                }
                Connection.DISCONNECTED -> {
                    logMsg("Disconnected")

                    disconnect()
                }
            }
        }

        override fun onConnectionFailed(errorCode: Int) {
            when (errorCode) {
                Connection.SOCKET_NOT_FOUND -> {
                    logMsg("Socket not found")

                }
                Connection.CONNECT_FAILED -> {
                    logMsg("Failed to connect")

                }
            }
            disconnect()
        }}

    private fun logMsg(msg: String) {
        Log.d(tag1, msg)
    }


}