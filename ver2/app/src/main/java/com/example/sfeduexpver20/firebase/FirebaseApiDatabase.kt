package com.example.sfeduexpver20.startpage.firebase

import android.util.Log
import com.google.firebase.database.*
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

/**
 * Класс для работы с Firebase Realtime Database
 * @author Rengerstar <vip.bekezin@mail.ru>
 * */
class FirebaseApiDatabase() {

    /**
     * Метод возвращающий список всех dataSnapshot переданного референса
     * @param referenceName - имя референса
     * @return listOfPartners - список всех dataSnapshot
     * */
    fun takeAll(referenceName: String, completion: (MutableList<DataSnapshot>) -> Unit) {
        val listOfPartners = mutableListOf<DataSnapshot>()
        FirebaseDatabase.getInstance().getReference(referenceName).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var count = 1
                for (partnerSnapshot in dataSnapshot.children) {
                    listOfPartners.add(dataSnapshot.child(count.toString()))
                    count++
                }
                completion(listOfPartners)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //Обработка ошибки
                println("Failed to read value: ${databaseError.toException()}")
            }
        })
    }

    /**
     * Метод возвращающий один конкретный dataSnapshot по переданному референсу и id
     * @param referenceName - имя референса
     * @param uid - id нужного элемента
     * @return dataSnapshot - нужный dataSnapshot
     * */
    fun takeOne(referenceName: String, uid: Int, completion: (DataSnapshot) -> Unit){
        FirebaseDatabase.getInstance().getReference(referenceName).child(uid.toString()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                completion(dataSnapshot)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Обработка ошибки
                Log.e("FirebaseError", "Failed to read the value", databaseError.toException())
            }
        })
    }

    /**
     * Метод возвращающий список всех dataSnapshot переданного референса
     * @param referenceName - имя референса
     * @return listOfPartners - список всех dataSnapshot
     * */
    fun takeCount(referenceName: String, completion: (Int) -> Unit) {
        FirebaseDatabase.getInstance().getReference(referenceName).addListenerForSingleValueEvent(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                completion(dataSnapshot.childrenCount.toInt())
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //Обработка ошибки
                println("Failed to read value: ${databaseError.toException()}")
            }
        })
    }

    fun regUser(hash: String, role: String, map: MutableMap<String,String>){
        val reference = FirebaseDatabase.getInstance().getReference("Users").child(role).child(hash)
        map.forEach{
            reference.child(it.key).setValue(it.value)
        }
    }

}