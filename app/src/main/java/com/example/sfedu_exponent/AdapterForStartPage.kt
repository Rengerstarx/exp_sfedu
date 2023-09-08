package com.example.sfedu_exponent

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class AdapterForStartPage(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 4 // Количество фрагментов в ViewPager2
    }

    override fun createFragment(position: Int): Fragment {
        // Возвращение фрагмента в соответствии с позицией
        return when (position) {
            0 -> StartFragment()
            1 -> AboutEXP()
            2 -> WhereWe()
            3 -> Partners()
            else -> StartFragment()
        }
    }


}
