package com.example.sfedu_exponent.Registration_page.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.sfedu_exponent.R
import com.google.firebase.database.FirebaseDatabase
import java.security.SecureRandom

class Register : Fragment() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var sharedPreferencesReg: SharedPreferences
    lateinit var role: String
    lateinit var hash: String
    lateinit var linearLayout: LinearLayout
    lateinit var editor: SharedPreferences.Editor


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Users")
        sharedPreferences = requireContext().getSharedPreferences("Roles", Context.MODE_PRIVATE)
        role = sharedPreferences.getString("Roles", "Guest").toString()
        linearLayout = view.findViewById(R.id.Liner)
        ShowEdit(view)
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            var marker = true
            for (i in 0 until linearLayout.childCount) {
                val childView: View = linearLayout.getChildAt(i)
                if (childView is EditText) {
                    if(childView.text.toString()=="" && childView.visibility==View.VISIBLE)
                        marker=false
                }
            }
            if(marker) {
                val surname = view.findViewById<EditText>(R.id.text_Surname).text
                val nameX = view.findViewById<EditText>(R.id.text_Name).text
                sharedPreferencesReg = requireContext().getSharedPreferences("RegHashId_${surname}_${nameX}", Context.MODE_PRIVATE)
                editor = sharedPreferencesReg.edit()
                hash=sharedPreferencesReg.getString("RegHashId_${surname}_${nameX}", "null").toString()
                if(hash=="null") {
                    hash = generateRandomHash()
                    editor.putString("RegHashId_${surname}_${nameX}", hash)
                    editor.apply()
                }
                reference.child(hash).child("Роль").setValue(role)
                for (i in 0 until linearLayout.childCount) {
                    val childView: View = linearLayout.getChildAt(i)
                    if (childView is EditText) {
                        reference.child(hash).child(childView.hint.toString())
                            .setValue(childView.text.toString())
                    }
                }
                requireActivity().supportFragmentManager.popBackStack()
            }else
                Toast.makeText(requireContext(), "Не все поля заполненны", Toast.LENGTH_LONG).show()
        }
        return view
    }

    fun generateRandomHash():String {
        val random = SecureRandom()
        val bytes = ByteArray(16)
        random.nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }

    fun ShowEdit(view: View){
        for (i in 0 until linearLayout.childCount) {
            val childView: View = linearLayout.getChildAt(i)
            if (childView is EditText) {
                childView.setText("")
            }
        }
        if(role=="Guest") {
            view.findViewById<EditText>(R.id.text_Work_Study).visibility = View.GONE
            view.findViewById<EditText>(R.id.text_Inters).visibility=View.GONE
        }
        else {
            view.findViewById<EditText>(R.id.text_Work_Study).visibility = View.VISIBLE
            view.findViewById<EditText>(R.id.text_Inters).visibility=View.VISIBLE
        }
        if(role!="Speaker"){
            view.findViewById<EditText>(R.id.text_ProjectName).visibility=View.GONE
            view.findViewById<EditText>(R.id.text_Intdrodaction).visibility=View.GONE
            view.findViewById<EditText>(R.id.text_Technologic).visibility=View.GONE
            view.findViewById<EditText>(R.id.text_Plan).visibility=View.GONE
            view.findViewById<EditText>(R.id.text_Stadi).visibility=View.GONE
            view.findViewById<EditText>(R.id.text_Dop).visibility=View.GONE
            view.findViewById<EditText>(R.id.text_Inters).visibility=View.GONE
        }else{
            view.findViewById<EditText>(R.id.text_ProjectName).visibility=View.VISIBLE
            view.findViewById<EditText>(R.id.text_Intdrodaction).visibility=View.VISIBLE
            view.findViewById<EditText>(R.id.text_Technologic).visibility=View.VISIBLE
            view.findViewById<EditText>(R.id.text_Plan).visibility=View.VISIBLE
            view.findViewById<EditText>(R.id.text_Stadi).visibility=View.VISIBLE
            view.findViewById<EditText>(R.id.text_Dop).visibility=View.VISIBLE
        }

    }



}