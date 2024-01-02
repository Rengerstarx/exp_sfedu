package com.example.sfeduexpver20.startpage.fragments.partners

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sfeduexpver20.R
import com.example.sfeduexpver20.databinding.PartnerBinding
import com.example.sfeduexpver20.startpage.fragments.partners.database.Partner
import com.squareup.picasso.Picasso


class PartnerAdapter(val listener: Listener): RecyclerView.Adapter<PartnerAdapter.PartnerHolder>() {
    private var PartnerList=ArrayList<Partner>()

    class PartnerHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = PartnerBinding.bind(item)
        fun bind(partner: Partner, listener: Listener) = with(binding){
            Picasso.get().load(partner.logo).into(imageCard)
            //partner.logo
            imageCard.setOnClickListener{
                listener.onClick(partner)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartnerHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.partner,parent,false)
        return  PartnerHolder(view)
    }

    override fun onBindViewHolder(holder: PartnerHolder, position: Int) {
        holder.bind(PartnerList[position], listener )
    }

    override fun getItemCount(): Int {
        return PartnerList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun partnerCreateElement(partner: Partner){
        PartnerList.add(partner)
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun partnerCreateAll(partnerList: Set<Partner>){
        val partnerList2 = mutableListOf<Partner>()
        partnerList.forEach {
            partnerList2.add(it)
        }
        println(partnerList2)
        PartnerList= partnerList2 as ArrayList<Partner>
        notifyDataSetChanged()
    }

    fun deleter(){
        var t=PartnerList.size
        var h=0
        while(h<t){
            PartnerList.removeAt(0)
            h+=1
        }
    }

    interface Listener{
        fun onClick(partner: Partner)
    }
}