package com.example.sfeduexpver20.startpage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.sfeduexpver20.R
import com.example.sfeduexpver20.startpage.adapters.AdapterStartpage
import com.example.sfeduexpver20.startpage.fragments.partners.ParntersModel
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {
    /**Переменные*/
    lateinit var viewPager: ViewPager2
    companion object {
        lateinit var currentInstance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val partnersmodel: ParntersModel = ParntersModel(this)
        FirebaseApp.initializeApp(this)
        /**Обьявление ViewPager2 и присвоение ему адаптера (по типу RecyclerView)*/
        viewPager = findViewById(R.id.pager)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        val adapter = AdapterStartpage(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        currentInstance = this
    }
}