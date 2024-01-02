package com.example.sfeduexpver20.startpage.firebase

import com.google.firebase.database.DataSnapshot
import com.google.firebase.storage.FirebaseStorage

/**
 * Класс для работы с Firebase Storage
 * @author Rengerstar <vip.bekezin@mail.ru>
 * */
class FirebaseApiStorage {

    fun getPicLogo(url: String, completion: (String) -> Unit){
        FirebaseStorage.getInstance().reference.child("Logotips/${url}").downloadUrl.addOnSuccessListener { uri ->
            val imageUrl = uri.toString()
            completion(imageUrl)
        }
    }

    fun getPicPhoto(urls: String, completion: (String) -> Unit){
        val urlMas = urls.split(" ").toList()
        var count = 0
        var listOfImages = ""
        urlMas.forEach {
            FirebaseStorage.getInstance().reference.child("Photos/${it}").downloadUrl.addOnSuccessListener { uri ->
                val imageUrl = uri.toString()
                listOfImages+= "${imageUrl}"
                count++
                if (count == urlMas.size)
                    completion(listOfImages)
                listOfImages+="|"
            }
        }
    }
}