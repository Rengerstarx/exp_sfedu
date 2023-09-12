package com.example.sfedu_exponent.Started_page.Adapters.RecuclerView

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sfedu_exponent.databinding.PartnerBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.sfedu_exponent.R
import com.example.sfedu_exponent.Started_page.DataClasses.Partner
import com.squareup.picasso.Picasso

class PartnerAdapter(val listener: Listener): RecyclerView.Adapter<PartnerAdapter.PartnerHolder>() {
    val PartnerList=ArrayList<Partner>()

    class PartnerHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = PartnerBinding.bind(item)
        fun bind(partner: Partner, listener: Listener) = with(binding){
            Picasso.get().load(partner.image).into(imageCard)
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
    fun PartnerCreate(url: String, uid: Int){
        PartnerList.add(Partner(PartnerList.size,url, uid))
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

    fun GetItemSize():String{
        return PartnerList.size.toString()
    }

    interface Listener{
        fun onClick(partner: Partner)
    }
}