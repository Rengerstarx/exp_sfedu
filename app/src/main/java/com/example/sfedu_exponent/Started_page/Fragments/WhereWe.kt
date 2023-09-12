package com.example.sfedu_exponent.Started_page.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.sfedu_exponent.R
import com.example.sfedu_exponent.Started_page.TakePermissions

class WhereWe : Fragment() {

    private lateinit var phoneCallPermissionManager: TakePermissions

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_where_we, container, false)
        val textPhone=view.findViewById<TextView>(R.id.textNumber)
        phoneCallPermissionManager = TakePermissions(requireActivity())
        textPhone.setOnClickListener {
            phoneCallPermissionManager.changePhone("89054592158")
            phoneCallPermissionManager.requestPhoneCallPermission()
        }
        return view
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        phoneCallPermissionManager.onRequestPermissionsResult(requestCode, grantResults)
    }

}