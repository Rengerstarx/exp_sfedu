package com.example.sfeduexpver20.startpage.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sfeduexpver20.startpage.fragments.aboutexp.AboutEXP
import com.example.sfeduexpver20.startpage.fragments.Contacts
import com.example.sfeduexpver20.startpage.fragments.partners.Partners
import com.example.sfeduexpver20.startpage.fragments.start.Start

/**
 * Класс адаптер для ViewPager2
 * @author Rengerstar <vip.bekezin@mail.ru>
 * */
class AdapterStartpage(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    /**Количество фрагментов в ViewPager2*/
    override fun getItemCount(): Int {
        return 4
    }

    /**@return фрагмент в соответствии с позицией*/
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> Start()
            1 -> AboutEXP()
            2 -> Contacts()
            3 -> Partners()
            else -> Start()
        }
    }
}