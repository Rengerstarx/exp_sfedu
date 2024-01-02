package com.example.sfedu_exponent.Registration_page.Fragments

import android.R
import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class Info_menu : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: Editor
    private val guestArray = "true true true true false false false false false false false false true true true"
    private val SpeakerArray = "true true true true true true true true true true true true true true true"
    private val OutherArray = "true true true true true true false false false false false false true true true"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(com.example.sfedu_exponent.R.layout.fragment_info_menu, container, false)

        sharedPreferences = requireContext().getSharedPreferences("Roles", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()

        view.findViewById<Button>(com.example.sfedu_exponent.R.id.buttonGuest).setOnClickListener {
            openRole("Guest", guestArray)
        }
        view.findViewById<Button>(com.example.sfedu_exponent.R.id.buttonInvestor).setOnClickListener {
            openRole("Investor",OutherArray)
        }
        view.findViewById<Button>(com.example.sfedu_exponent.R.id.buttonSpeaker).setOnClickListener {
            openRole("Speaker",SpeakerArray)
        }
        view.findViewById<Button>(com.example.sfedu_exponent.R.id.buttonSponsor).setOnClickListener {
            openRole("Sponsor",OutherArray)
        }
        view.findViewById<Button>(com.example.sfedu_exponent.R.id.buttonExpert).setOnClickListener {
            openRole("Expert",OutherArray)
        }
        return view
    }

    fun openRole(role: String, arrayFields: String){
        editor.putString("Roles", role)
        editor.putString("ArrayFields", arrayFields)
        editor.apply()
        val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(com.example.sfedu_exponent.R.id.frame_layout, Info_about_roles()).addToBackStack(null)
        fragmentTransaction.commit()
    }

}