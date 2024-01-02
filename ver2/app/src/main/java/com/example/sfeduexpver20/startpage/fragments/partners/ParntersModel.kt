package com.example.sfeduexpver20.startpage.fragments.partners

import android.content.Context
import com.example.sfeduexpver20.startpage.firebase.FirebaseApiDatabase
import com.example.sfeduexpver20.startpage.firebase.FirebaseApiStorage
import com.example.sfeduexpver20.startpage.fragments.partners.database.DaoPartner
import com.example.sfeduexpver20.startpage.fragments.partners.database.Partner
import com.example.sfeduexpver20.startpage.fragments.partners.database.PartnerDatabase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CountDownLatch
import kotlin.concurrent.thread

interface OnDataReadyListener {
    fun onDataReady(partnersSet: Set<Partner>)
}

class ParntersModel {

    constructor(context: Context){
        db = PartnerDatabase.getDatabase(context)
        dao = db.getDao()
        thread{
            countElement = dao.getCount()
            setOfPartners = dao.getAllPartners().toSet()
        }
        firebase = FirebaseApiDatabase()
        firebaseStorage = FirebaseApiStorage()
        updateDB()
    }

    private val db: PartnerDatabase
    private val dao: DaoPartner
    private var countElement: Int = 0
    private var setOfPartners: Set<Partner> = setOf()
    private val firebase: FirebaseApiDatabase
    private val firebaseStorage: FirebaseApiStorage
    private var onDataReadyListener: OnDataReadyListener? = null

    fun setOnDataReadyListener(listener: OnDataReadyListener) {
        onDataReadyListener = listener
    }

    fun returnPartnersSet(): Set<Partner>{
        return setOfPartners
    }

    private fun updateDB() {
        firebase.takeCount("Partners") { newCount ->
            countElement = newCount
            firebase.takeAll("Partners") { dtList ->
                val newSet = parseDatasnapshotList(dtList)
                setOfPartners.subtract(newSet).forEach {
                    runBlocking {
                        dao.deleteObject(it)
                    }
                }
                val latch = CountDownLatch(1)
                thread {
                    newSet.subtract(setOfPartners).forEach {
                        dao.insertItem(it)
                    }
                    latch.countDown()
                }
                latch.await()
                setOfPartners = newSet
                loadPic()
            }
        }
    }

    private fun loadPic(){
        setOfPartners.forEach { partner ->
            firebaseStorage.getPicLogo(partner.logo){
                partner.logo = it
            }
            firebaseStorage.getPicPhoto(partner.photos){
                partner.photos = it
                if (partner == setOfPartners.last())
                    onDataReadyListener?.onDataReady(setOfPartners)
            }
        }
    }


    private fun parseDatasnapshotList(dataSnapshot: MutableList<DataSnapshot>): Set<Partner>{
        val set: MutableSet<Partner> = mutableSetOf()
        //println(dataSnapshot[0])
        dataSnapshot.forEach { snapshot ->
            val par = Partner(
                id =  null,
                logo = snapshot.child("LogoName").value.toString(),
                uid = snapshot.child("KeyNumber").value.toString().toInt(),
                name = snapshot.child("Name").value.toString(),
                address = snapshot.child("Address").value.toString(),
                photos = snapshot.child("Photo").value.toString(),
                phoneNumber = snapshot.child("PhoneNumber").value.toString(),
                tellegram = snapshot.child("Tellegram").value.toString(),
                vkontakte = snapshot.child("VKontakte").value.toString(),
                web_site = snapshot.child("Web_site").value.toString(),
                youTube = snapshot.child("YouTube").value.toString()
            )
            set.add(par)
        }
        return set
    }

    /*private fun parseDatasnapshot(dataSnapshot: DataSnapshot): Partner{

    }*/

}