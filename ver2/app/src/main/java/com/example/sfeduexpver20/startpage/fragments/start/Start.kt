package com.example.sfeduexpver20.startpage.fragments.start

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sfeduexpver20.R
import com.example.sfeduexpver20.RegistrationActivity
import com.example.sfeduexpver20.coordinators.CoordinatorsActivity
import com.example.sfeduexpver20.databinding.FragmentStartBinding
import com.example.sfeduexpver20.startpage.MainActivity
import com.linroid.filtermenu.library.FilterMenu
import com.linroid.filtermenu.library.FilterMenuLayout
import kotlin.concurrent.thread

class Start : Fragment() {

    private lateinit var binding: FragmentStartBinding
    private lateinit var filterMenu: FilterMenuLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater,container,false)
        filterMenu = binding.menu
        menuInit()
        thread{
            typingText(binding, StartModel(resources).takeLable())
        }
        binding.textGo.setOnClickListener {
            MainActivity.currentInstance.viewPager.setCurrentItem(1)
        }
        return binding.root
    }

    private fun typingText(binding: FragmentStartBinding, lable: String){
        val stringBuilder = StringBuilder()
        requireActivity().runOnUiThread {
            binding.textNewStep.text=""
        }
        for (letter in lable) {
            stringBuilder.append(letter)
            Thread.sleep(100)
            requireActivity().runOnUiThread {
                binding.textNewStep.text = stringBuilder.toString()
            }
        }
    }

    private fun menuInit(){
        filterMenu.expandedRadius=dpToPx(96f, requireContext()).toInt()
        filterMenu.collapsedRadius=dpToPx(24f, requireContext()).toInt()
        filterMenu.bottom=dpToPx(50f, requireContext()).toInt()
        filterMenu.right=dpToPx(50f, requireContext()).toInt()
        val first = FilterMenu.Builder(requireContext())
            .addItem(R.drawable.phone)
            .attach(filterMenu)
            .withListener(object : FilterMenu.OnMenuChangeListener {
                override fun onMenuItemClick(view: View, position: Int) {
                    val intent = Intent(requireActivity(), CoordinatorsActivity::class.java)
                    startActivity(intent)
                }
                override fun onMenuCollapse() {
                }
                override fun onMenuExpand() {
                }
            }).build()
        val second = FilterMenu.Builder(requireContext())
            .addItem(R.drawable.registar_ico)
            .attach(filterMenu)
            .withListener(object : FilterMenu.OnMenuChangeListener {
                override fun onMenuItemClick(view: View, position: Int) {
                    val intent = Intent(requireActivity(), RegistrationActivity::class.java)
                    startActivity(intent)
                }
                override fun onMenuCollapse() {
                }
                override fun onMenuExpand() {
                }
            }).build()
    }

    private fun dpToPx(dp: Float, context: Context): Float {
        val density = context.resources.displayMetrics.density
        return dp * density
    }

}