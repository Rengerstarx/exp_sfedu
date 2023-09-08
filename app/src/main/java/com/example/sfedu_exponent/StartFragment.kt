package com.example.sfedu_exponent

import android.animation.Animator
import android.animation.AnimatorInflater
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.TranslateAnimation
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2

class StartFragment : Fragment() {

    private lateinit var typingt: TextView
    private lateinit var backgroundThread: HandlerThread
    private lateinit var backgroundHandler: Handler

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start, container, false)
        typingt = view.findViewById(R.id.textNewStep)
        typingt.text = ""
        view.findViewById<TextView>(R.id.textGo).setOnClickListener{
            MainActivity.currentInstance.switchToFragment(1)
        }
        return view
    }

    override fun onResume() {
        super.onResume()
        backgroundThread = HandlerThread("BackgroundThread")
        backgroundThread.start()
        backgroundHandler = Handler(backgroundThread.looper)
        backgroundHandler.post(Runnable {
            printText()
        })
    }

    private fun printText() {
        val characters = arrayOf("Н", "о", "в", "а", "я", " ", "с", "т", "е", "п", "е", "н", "ь", " ", "р", "а", "з", "в", "и", "т", "и", "я", " ", "т", "в", "о", "е", "г", "о", " ", "п", "р", "о", "е", "к", "т", "а")
        for (character in characters) {
            // Имитируем задержку
            Thread.sleep(50)
            // Обновляем UI в главном потоке
            Handler(Looper.getMainLooper()).post {
                typingt.append(character)
            }
        }
    }

    private fun clearText(){
        Handler(Looper.getMainLooper()).post {
            typingt.text=""
        }
    }

    override fun onPause() {
        super.onPause()
        backgroundHandler.removeCallbacksAndMessages(null)
        clearText()
    }

}