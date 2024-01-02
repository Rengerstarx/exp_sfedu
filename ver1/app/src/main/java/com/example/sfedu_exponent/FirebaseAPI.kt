package com.example.sfedu_exponent

import android.provider.ContactsContract.Data
import android.util.Log
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.example.sfedu_exponent.Started_page.Adapters.RecuclerView.PartnerAdapter
import com.example.sfedu_exponent.Started_page.DataClasses.Partner
import com.google.firebase.database.*
import kotlinx.coroutines.*
import kotlin.coroutines.suspendCoroutine
import com.google.firebase.storage.FirebaseStorage

class FirebaseAPI(private val database: FirebaseDatabase, private val storage: FirebaseStorage? = null) {

    fun TakeAllPartnersDataList(completion: (MutableList<Partner>) -> Unit) {
        val listOfPartners = mutableListOf<Partner>()
        database.getReference("Partners").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                var counter: Long = 0
                val partnerCount = dataSnapshot.childrenCount
                for (partnerSnapshot in dataSnapshot.children) {
                    val key = partnerSnapshot.key?.toInt()
                    val logoName = partnerSnapshot.child("LogoName").value
                    val _uid = partnerSnapshot.child("KeyNumber").value.toString().toInt()
                    if (storage != null) {
                        storage.reference.child("Logotips/${logoName}").downloadUrl.addOnSuccessListener { uri ->
                            val imageUrl = uri.toString()
                            listOfPartners.add(Partner(key!!, imageUrl, _uid))
                            println(imageUrl)
                            counter++
                            if (counter == partnerCount) {
                                completion(listOfPartners)
                            }
                        }
                    } else {
                        Log.e("FirebaseAPI", "No storage data")
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle error
                println("Failed to read value: ${databaseError.toException()}")
            }
        })
    }




    fun TakeAllPartnersDataSnapshot():MutableList<DataSnapshot>{
        val ListOfPartners = mutableListOf<DataSnapshot>()
        database.getReference("Partners").addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (partnerSnapshot in dataSnapshot.children) {
                    ListOfPartners.add(dataSnapshot)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //Обработка ошибки
                println("Failed to read value: ${databaseError.toException()}")
            }
        })
        return ListOfPartners
    }

    fun TakePartner(uid: Int): DataSnapshot?{
        var dataSnapshotResult: DataSnapshot? = null
        database.getReference("Partners").child(uid.toString()).addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshotResult=dataSnapshot
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    // Обработка ошибки
                    Log.e("FirebaseError", "Failed to read the value", databaseError.toException())
                }
        })
        if (dataSnapshotResult==null)
            Log.e("FirebaseAPI", "Null of dataSnapshot")
        return dataSnapshotResult
    }

}