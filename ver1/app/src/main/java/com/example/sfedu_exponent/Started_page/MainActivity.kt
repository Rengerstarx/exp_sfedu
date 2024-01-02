package com.example.sfedu_exponent.Started_page

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.sfedu_exponent.R
import com.example.sfedu_exponent.Started_page.Adapters.ViewPager2.AdapterForStartPage

class MainActivity : AppCompatActivity() {

    /**Переменные*/
    lateinit var viewPager: ViewPager2
    companion object {
        lateinit var currentInstance: MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**Обьявление ViewPager2 и присвоение ему адаптера (по типу RecyclerView)*/
        viewPager = findViewById(R.id.pager)
        viewPager.orientation = ViewPager2.ORIENTATION_VERTICAL
        val adapter = AdapterForStartPage(supportFragmentManager, lifecycle)
        viewPager.adapter = adapter
        currentInstance = this
    }

    fun switchToFragment(position: Int) {
        viewPager.setCurrentItem(position, true)
    }
}