package com.example.sfeduexpver20

import android.content.Context
import com.example.sfeduexpver20.startpage.firebase.FirebaseApiDatabase
import com.example.sfeduexpver20.startpage.firebase.FirebaseApiStorage
import com.example.sfeduexpver20.startpage.fragments.partners.database.PartnerDatabase
import java.security.SecureRandom
import kotlin.concurrent.thread

class RegisterViewModel {

    private val firebase: FirebaseApiDatabase = FirebaseApiDatabase()

    fun addUser(role: String, map: MutableMap<String,String>){
        firebase.regUser(generateRandomHash(), role, map)
    }

    private fun generateRandomHash(): String {
        val random = SecureRandom()
        val bytes = ByteArray(16)
        random.nextBytes(bytes)
        return bytes.joinToString("") { "%02x".format(it) }
    }

}