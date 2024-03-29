package com.example.carenta

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.edit
import com.example.carenta.databinding.FragmentDetail2Binding
import com.example.carenta.databinding.FragmentProfileBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class profile : Fragment() {
    lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Here we are getting the user's data from pref plus the ones he inserts
        val pref = requireActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE)
        binding.fullNameedit.hint = pref.getString("userName","error")
        binding.phoneNumedit.hint = pref.getString("phoneNumber","error")
        binding.logoutBtn.setOnClickListener{
            val intent = Intent(requireActivity(), signin::class.java)
            startActivity(intent)
            requireActivity().finish()
            val pref = requireActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE)
            pref.edit {
                putBoolean("connected",false)
            }
        }

        // the user can modify his personal information
        binding.modifyBtn.setOnClickListener {
            // we make sure that he is not sending empty data
            if (TextUtils.isEmpty(binding.fullNameedit.getText()) || TextUtils.isEmpty(binding.phoneNumedit.getText())
                || TextUtils.isEmpty(binding.Passwordedit.getText())) {
                Toast.makeText(requireActivity(), "All Information Are Required", Toast.LENGTH_LONG)
                    .show()
                if (TextUtils.isEmpty(binding.fullNameedit.getText())) {
                    binding.fullNameedit.setError("User Name required!")
                }
                if (TextUtils.isEmpty(binding.phoneNumedit.getText())) {
                    binding.phoneNumedit.setError("Phone number required!")
                }
                if (TextUtils.isEmpty(binding.Passwordedit.getText())) {
                    binding.Passwordedit.setError("Password required!")
                }

            } else {
                updateUser(binding.fullNameedit.text.toString(), binding.phoneNumedit.text.toString(),
                    binding.Passwordedit.text.toString(),pref.getInt("idUser",0))
            }
        }
        // the button the switch to the dark mode
        binding.darkmode.setOnClickListener{
            toggleTheme()
        }
    }

    // the function that will update the user's information in the data base
    private fun updateUser(name: String, tlf: String, pwd: String,id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitService.endpoint.updateuser(ModifyUserCreds(name,tlf,pwd,id))
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    binding.fullNameedit.hint = name
                    binding.phoneNumedit.hint = tlf
                    val pref = requireActivity().getSharedPreferences("fileName", Context.MODE_PRIVATE)
                    pref.edit{
                        putString("userName", name)
                        putString("phoneNumber", tlf)
                    }
                    Toast.makeText(requireActivity(),"Information updated",Toast.LENGTH_LONG).show()
                }
                else {
                    Toast.makeText(requireActivity(), "Error", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // function to switch to the dar mode
    fun toggleTheme() {
        val isDarkMode = AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES
        AppCompatDelegate.setDefaultNightMode(if (isDarkMode) AppCompatDelegate.MODE_NIGHT_NO else AppCompatDelegate.MODE_NIGHT_YES)
    }
}