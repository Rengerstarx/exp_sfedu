package com.example.sfeduexpver20

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import com.example.sfeduexpver20.databinding.FragmentRegisterBinding
import com.example.sfeduexpver20.startpage.MainActivity

class Register : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private var arrayRole: Array<String> = arrayOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater,container,false)
        val sharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val storedString = sharedPreferences.getString("role", "")
        when (storedString) {
            "Speak" -> {
                arrayRole = resources.getStringArray(R.array.Speaker)
            }
            "Guest" -> {
                arrayRole = resources.getStringArray(R.array.Guest)
            }
            else -> {
                arrayRole = resources.getStringArray(R.array.Other)
            }
        }
        showEdit()
        binding.button2.setOnClickListener {
            val map = collectInfo(mutableMapOf())
            val allValuesNotEmpty = map.values.all { it != "" }
            if (allValuesNotEmpty) {
                RegisterViewModel().addUser("Speaker", map)
                val intent = Intent(requireActivity(), MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(requireContext(), "Не все поля заполненны", Toast.LENGTH_LONG).show()
            }
        }
        return binding.root
    }

    private fun showEdit(){
        for (i in 0 until binding.liner.childCount) {
            val childView: View = binding.liner.getChildAt(i)
            if (childView is EditText) {
                if (arrayRole[i].toBoolean())
                    childView.visibility = View.VISIBLE
                else
                    childView.visibility = View.GONE
                childView.setText("")
            }
        }
    }

    private fun collectInfo(map: MutableMap<String,String>): MutableMap<String,String>{
        for (i in 0 until binding.liner.childCount) {
            val childView: View = binding.liner.getChildAt(i)
            if (childView is EditText) {
                map[childView.hint.toString()] = childView.text.toString()
            }
        }
        return map
    }

}