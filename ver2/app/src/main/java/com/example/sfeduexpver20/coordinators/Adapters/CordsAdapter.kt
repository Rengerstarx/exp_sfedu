package com.example.sfedu_exponent.Admins_page.Adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sfedu_exponent.Admins_page.DataClasses.Cords
import com.example.sfeduexpver20.R
import com.example.sfeduexpver20.databinding.CordBinding
import com.squareup.picasso.Picasso

class CordsAdapter(val listener: Listener): RecyclerView.Adapter<CordsAdapter.CordsHolder>() {
    val CordList=ArrayList<Cords>()

    class CordsHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = CordBinding.bind(item)
        fun bind(cord: Cords, listener: Listener) = with(binding){
            Picasso.get().load(cord.photo).into(imPhoto)
            textName.text=cord.name
            textSurname.text=cord.surname
            ConstCard.setOnClickListener{
                listener.onClick(cord)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CordsHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.cord,parent,false)
        return  CordsHolder(view)
    }

    override fun onBindViewHolder(holder: CordsHolder, position: Int) {
        holder.bind(CordList[position], listener )
    }

    override fun getItemCount(): Int {
        return CordList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun CordCreate(name:String, surname:String, phone: String, photo: String, vk: String){
        CordList.add(Cords(CordList.size,name, surname, phone, photo, vk))
        notifyDataSetChanged()
    }

    fun deleter(){
        var t=CordList.size
        var h=0
        while(h<t){
            CordList.removeAt(0)
            h+=1
        }
    }

    fun GetItemSize():String{
        return CordList.size.toString()
    }

    interface Listener{
        fun onClick(cord: Cords)
    }
}