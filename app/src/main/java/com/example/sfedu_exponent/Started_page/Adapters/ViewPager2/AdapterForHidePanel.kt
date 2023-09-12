package com.example.sfedu_exponent.Started_page.Adapters.ViewPager2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.sfedu_exponent.R
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

class AdapterForHidePanel : RecyclerView.Adapter<AdapterForHidePanel.ImageViewHolder>() {
    private var images: MutableList<String> = arrayListOf<String>() as MutableList<String>

    /**Добавляем список картинок*/
    fun addImage(imageResId: MutableList<String>) {
        images = imageResId
    }

    /**Создание холдера*/
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.image_smena, parent, false)
        return ImageViewHolder(view)
    }

    /**Подстановка новое картинки в случае конца таймера или ручной смены*/
    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageResId = images[position]
        val storage = FirebaseStorage.getInstance()
        /**Подгрузка с БД*/
        storage.reference.child("Photos/${imageResId}").downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            /**Суем в пикасо и отрисовываем*/
            Picasso.get().load(imageUrl).into(holder.imageView)
        }
    }

    /**Возвращение рааззмера текущеего ViewPager2*/
    override fun getItemCount(): Int {
        return images.size
    }

    /**Находим необходимый нам ImageView*/
    class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView4)
    }
}

