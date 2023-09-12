package com.example.sfedu_exponent.Registration_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.example.sfedu_exponent.R
import com.example.sfedu_exponent.Registration_page.Fragments.Info_menu

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val fragment = Info_menu()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).commit()
    }
}