package com.example.sfedu_exponent.Started_page.Adapters.ViewPager2

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.sfedu_exponent.Started_page.Fragments.AboutEXP
import com.example.sfedu_exponent.Started_page.Fragments.Partners
import com.example.sfedu_exponent.Started_page.Fragments.StartFragment
import com.example.sfedu_exponent.Started_page.Fragments.WhereWe

class AdapterForStartPage(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    /**Количество фрагментов в ViewPager2*/
    override fun getItemCount(): Int {
        return 4
    }

    /**Возвращение фрагмента в соответствии с позицией*/
    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> StartFragment()
            1 -> AboutEXP()
            2 -> WhereWe()
            3 -> Partners()
            else -> StartFragment()
        }
    }

}
