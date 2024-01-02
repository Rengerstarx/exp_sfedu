package com.example.sfeduexpver20

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.sfeduexpver20.databinding.FragmentRegisterChhoseBinding
import com.example.sfeduexpver20.databinding.FragmentStartBinding

class RegisterChhose : Fragment() {

    private lateinit var binding: FragmentRegisterChhoseBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterChhoseBinding.inflate(inflater,container,false)
        binding.button.setOnClickListener {
            showQestion("Speak")
        }
        binding.button3.setOnClickListener {
            showQestion("Inv")
        }
        binding.button4.setOnClickListener {
            showQestion("Exp")
        }
        binding.button5.setOnClickListener {
            showQestion("Gst")
        }
        binding.button6.setOnClickListener {
            showQestion("Stend")
        }
        return binding.root
    }

    private fun showQestion(str: String) {
        val sharedPreferences = requireContext().getSharedPreferences("myPreferences", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var desc = ""
        when (str) {
            "Gst" -> {
                desc = getString(R.string.Gst)
            }
            "Inv" -> {
                desc = getString(R.string.Inv)
            }
            "Exp" -> {
                desc = getString(R.string.Exp)
            }
            else -> {
                desc = getString(R.string.Speak)
            }
        }
        editor.putString("role", str)
        editor.apply()
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Описание роли")
        builder.setMessage(desc)
        builder.setPositiveButton("Зарегистрироваться") { dialog, which ->
            val fragmentB = Register()
            val fragmentManager = requireActivity().supportFragmentManager
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragmentB)
            transaction.addToBackStack(null)
            transaction.commit()
        }
        builder.setNegativeButton("Отмена") { dialog, which ->

        }
        val dialog = builder.create()
        dialog.show()
    }

}