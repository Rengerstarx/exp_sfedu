package com.example.sfeduexpver20.startpage.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sfeduexpver20.databinding.FragmentContactsBinding
import com.example.sfeduexpver20.startpage.phone.Phone

class Contacts : Fragment() {

    private lateinit var binding: FragmentContactsBinding
    private var phone: Phone? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentContactsBinding.inflate(inflater,container,false)
        phone= Phone(this, "tel:89054592158")
        binding.textNumber.setOnClickListener {
           phone!!.makeCall()
        }
        return binding.root
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        phone?.takePerm(requestCode, permissions, grantResults)
    }

}