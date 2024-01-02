package com.example.sfeduexpver20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        val fragment = RegisterChhose()
        supportFragmentManager.beginTransaction().replace(R.id.frame_layout,fragment).commit()
    }
}