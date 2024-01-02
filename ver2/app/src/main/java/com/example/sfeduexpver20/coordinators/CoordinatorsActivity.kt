package com.example.sfeduexpver20.coordinators

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sfedu_exponent.Admins_page.Adapters.CordsAdapter
import com.example.sfedu_exponent.Admins_page.DataClasses.Cords
import com.example.sfeduexpver20.R
import com.example.sfeduexpver20.coordinators.Adapters.TakePermissions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class CoordinatorsActivity : AppCompatActivity(), CordsAdapter.Listener {

    private lateinit var phoneCallPermissionManager: TakePermissions

    /**Переменные для работы с Firebase*/
    val adapter= CordsAdapter(this)
    val storage = FirebaseStorage.getInstance()
    val database = FirebaseDatabase.getInstance()
    val reference = database.getReference("Admins")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cordinators)
        phoneCallPermissionManager = TakePermissions(this)
        findViewById<RecyclerView>(R.id.rcView2).layoutManager = GridLayoutManager(this,2)
        findViewById<RecyclerView>(R.id.rcView2).adapter=adapter
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Перебираем каждый объект и выводим значение поля "LogoName"
                for (partnerSnapshot in dataSnapshot.children) {
                    val Name = partnerSnapshot.child("Name").value.toString()
                    val Surname = partnerSnapshot.child("Surname").value.toString()
                    val Photo = partnerSnapshot.child("Photo").value.toString()
                    val VK = partnerSnapshot.child("VK").value.toString()
                    val Phone = partnerSnapshot.child("Phone").value.toString()
                    storage.reference.child("Admins/${Photo}").downloadUrl.addOnSuccessListener { uri ->
                        val imageUrl = uri.toString()
                        adapter.CordCreate(Name,Surname,Phone, imageUrl, VK)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                //Обработка ошибки
                println("Failed to read value: ${databaseError.toException()}")
            }
        })
    }

    override fun onClick(cord: Cords) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Связь с координатором")
        builder.setMessage("Как вы хотите связаться с этим координатором?")
        builder.setPositiveButton("Написать") { dialog, which ->
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(cord.vk))
            startActivity(intent)
        }
        builder.setNegativeButton("Позвонить") { dialog, which ->
            phoneCallPermissionManager.changePhone(cord.phone)
            phoneCallPermissionManager.requestPhoneCallPermission()
        }
        val dialog = builder.create()
        dialog.show()
    }
}