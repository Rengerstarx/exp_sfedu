package com.example.sfeduexpver20.startpage.fragments.partners

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.sfeduexpver20.R
import com.example.sfeduexpver20.databinding.FragmentPartnersBinding
import com.example.sfeduexpver20.startpage.fragments.partners.database.Partner
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator
import java.util.*

class Partners : Fragment(), OnDataReadyListener, PartnerAdapter.Listener {

    private lateinit var binding: FragmentPartnersBinding
    val adapter= PartnerAdapter(this)
    private lateinit var adapterPager: PartnerPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPartnersBinding.inflate(inflater, container, false)
        adapterPager = PartnerPagerAdapter()
        val model = ParntersModel(context = requireContext())
        model.setOnDataReadyListener(this)
        return binding.root
    }

    override fun onDataReady(partnersSet: Set<Partner>) {
        binding.partnerView.layoutManager = LinearLayoutManager(requireContext())
        binding.partnerView.adapter=adapter
        adapter.partnerCreateAll(partnerList = partnersSet)
    }

    override fun onClick(partner: Partner) {
        showBottomSheet(partner)
    }

    private fun showBottomSheet(partner: Partner){
        val dialogView = layoutInflater.inflate(R.layout.bottom_sheet_partners,null)
        val viewPager = dialogView.findViewById<ViewPager2>(R.id.viewPager)
        val indicator=dialogView.findViewById<WormDotsIndicator>(R.id.worm_dots_indicator)
        val dialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        dialog.show()

        setInfo(partner,dialogView)

        adapterPager.addImage(partner.photos.split("|").toMutableList())
        viewPager.adapter = adapterPager
        indicator.setViewPager2(viewPager)
        val timer = Timer()
        val delay = 3000 // задержка между сменой картинок (5 секунд)
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                requireActivity().runOnUiThread {
                    if (viewPager.currentItem < adapterPager.itemCount - 1) {
                        viewPager.currentItem++
                    } else {
                        viewPager.currentItem = 0
                    }
                }
            }
        }, 0L, delay.toLong())
    }

    private fun setInfo(partner: Partner, view: View){

    }

}
