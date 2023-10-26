package com.example.sfedu_exponent.Started_page.Fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.sfedu_exponent.Admins_page.AdminActivity
import com.example.sfedu_exponent.Live_page.LiveActivity
import com.example.sfedu_exponent.R
import com.example.sfedu_exponent.Registration_page.RegistrationActivity
import com.example.sfedu_exponent.Started_page.MainActivity
import com.linroid.filtermenu.library.FilterMenu
import com.linroid.filtermenu.library.FilterMenuLayout

class StartFragment : Fragment() {

    private lateinit var typingt: TextView
    private lateinit var backgroundThread: HandlerThread
    private lateinit var backgroundHandler: Handler

    @SuppressLint("MissingInflatedId")
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

        val layout: FilterMenuLayout = view.findViewById(R.id.filter_menu)
        layout.expandedRadius=dpToPx(96f, requireContext()).toInt()
        layout.collapsedRadius=dpToPx(24f, requireContext()).toInt()
        layout.bottom=dpToPx(50f, requireContext()).toInt()
        layout.right=dpToPx(50f, requireContext()).toInt()
        val first = FilterMenu.Builder(requireContext())
            .addItem(R.drawable.phone)
            //.inflate(R.menu....)//inflate  menu resource
            .attach(layout)
            .withListener(object : FilterMenu.OnMenuChangeListener {
                override fun onMenuItemClick(view: View, position: Int) {
                    val intent = Intent(requireActivity(), AdminActivity::class.java)
                    startActivity(intent)
                }
                override fun onMenuCollapse() {
                }
                override fun onMenuExpand() {
                }
            })
            .build()
        val second = FilterMenu.Builder(requireContext())
            .addItem(R.drawable.registar_ico)
            .attach(layout)
            .withListener(object : FilterMenu.OnMenuChangeListener {
                override fun onMenuItemClick(view: View, position: Int) {
                    val intent = Intent(requireActivity(), RegistrationActivity::class.java)
                    startActivity(intent)
                }
                override fun onMenuCollapse() {
                }
                override fun onMenuExpand() {
                }
            })
            .build()
        val third = FilterMenu.Builder(requireContext())
            .addItem(R.drawable.schedule_ico)
            .attach(layout)
            .withListener(object : FilterMenu.OnMenuChangeListener {
                override fun onMenuItemClick(view: View, position: Int) {
                    val intent = Intent(requireActivity(), LiveActivity::class.java)
                    startActivity(intent)
                }
                override fun onMenuCollapse() {
                }
                override fun onMenuExpand() {
                }
            })
            .build()

        return view
    }

    fun dpToPx(dp: Float, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
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