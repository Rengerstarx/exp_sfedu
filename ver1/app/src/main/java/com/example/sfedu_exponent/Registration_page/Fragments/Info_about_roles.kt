package com.example.sfedu_exponent.Registration_page.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.example.sfedu_exponent.R

class Info_about_roles : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var role: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_info_about_roles, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Roles", Context.MODE_PRIVATE)
        role = sharedPreferences.getString("Roles", "Guest").toString()
        view.findViewById<TextView>(R.id.textRole).text=role
        view.findViewById<Button>(R.id.button).setOnClickListener {
            val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frame_layout, Register()).addToBackStack(null)
            fragmentTransaction.commit()
        }
        return view
    }

}