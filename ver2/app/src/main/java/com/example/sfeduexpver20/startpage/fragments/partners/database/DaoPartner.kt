package com.example.sfeduexpver20.startpage.fragments.partners.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DaoPartner {
    @Insert
    fun insertItem(partner: Partner)
    @Query("SELECT * FROM Partners")
    fun getAllPartners(): List<Partner>
    @Query("SELECT * FROM Partners WHERE id = :id")
    fun getPartnerById(id: Int): Partner
    @Query("SELECT COUNT(*) FROM Partners")
    fun getCount(): Int
    @Query("SELECT COUNT(*) FROM Partners WHERE id = :id")
    fun getCountById(id: Int): Int
    @Delete
    suspend fun deleteObject(partner: Partner)
}