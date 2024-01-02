package com.example.sfeduexpver20.startpage.fragments.partners.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Partner::class], version = 1)
abstract class PartnerDatabase: RoomDatabase() {

    abstract fun getDao(): DaoPartner

    companion object{
        fun getDatabase(context: Context): PartnerDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                PartnerDatabase::class.java,
                "test3.db"
            ).build()
        }
    }
}