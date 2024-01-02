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

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sharedPreferencesReg: SharedPreferences
    private lateinit var role: String
    private lateinit var hash: String
    private lateinit var linearLayout: LinearLayout
    private lateinit var editor: SharedPreferences.Editor
    private var ArrayRole: MutableList<Boolean> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_register, container, false)
        val database = FirebaseDatabase.getInstance()
        val reference = database.getReference("Users")
        sharedPreferences = requireContext().getSharedPreferences("Roles", Context.MODE_PRIVATE)
        role = sharedPreferences.getString("Roles", "Guest").toString()
        for (s in sharedPreferences.getString("ArrayFields", "").toString().split(" ")) {
            ArrayRole.add(s.toBoolean())
        }
        linearLayout = view.findViewById(R.id.Liner)
        showEdit(view)
        view.findViewById<Button>(R.id.button2).setOnClickListener {
            var marker = true
            for (i in 0 until linearLayout.childCount) {
                val childView: View = linearLayout.getChildAt(i)
                if (childView is EditText) {
                    if (childView.text.toString() == "" && childView.visibility == View.VISIBLE)
                        marker = false
                }
            }
            if (marker) {
                val surname = view.findViewById<EditText>(R.id.text_Surname).text
                val nameX = view.findViewById<EditText>(R.id.text_Name).text
                sharedPreferencesReg =
                    requireContext().getSharedPreferences("RegHashId_${surname}_${nameX}", Context.MODE_PRIVATE)
                editor = sharedPreferencesReg.edit()
                hash = sharedPreferencesReg.getString("RegHashId_${surname}_${nameX}", "null").toString()
                if (hash == "null") {
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
            } else
                Toast.makeText(requireContext(), "Не все поля заполнены", Toast.LENGTH_LONG).show()
        }
        return view
    }

    private fun generateRandomHash(): String {
        val random = SecureRandom()
        val bytes = ByteArray(16)
        random.nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }

    private fun showEdit(view: View) {
        for (i in 0 until linearLayout.childCount) {
            val childView: View = linearLayout.getChildAt(i)
            if (childView is EditText) {
                if (ArrayRole[i] == true)
                    childView.visibility = View.VISIBLE
                else
                    childView.visibility = View.GONE
                childView.setText("")
            }
        }
    }
}
