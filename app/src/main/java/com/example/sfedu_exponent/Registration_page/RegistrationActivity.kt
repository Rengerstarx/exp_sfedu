package com.example.sfedu_exponent.Registration_page

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sfedu_exponent.R
import com.example.sfedu_exponent.Registration_page.Fragments.Info_about_roles
import com.example.sfedu_exponent.Registration_page.Fragments.Info_menu
import com.example.sfedu_exponent.Registration_page.Fragments.Register


class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.sfedu_exponent.R.layout.activity_registration)
        val fragment = Info_menu()
        supportFragmentManager.beginTransaction().replace(com.example.sfedu_exponent.R.id.frame_layout,fragment).commit()
    }

}