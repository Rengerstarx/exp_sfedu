package com.example.sfeduexpver20.startpage.fragments.partners.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Partners")
data class Partner (
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    @ColumnInfo(name = "Logo") var logo: String,
    @ColumnInfo(name = "Uid") val uid: Int,
    @ColumnInfo(name = "Name") val name: String,
    @ColumnInfo(name = "Address") val address: String,
    @ColumnInfo(name = "Photos") var photos: String,
    @ColumnInfo(name = "PhoneNumber") val phoneNumber: String,
    @ColumnInfo(name = "Tellegram") val tellegram: String,
    @ColumnInfo(name = "VKontakte") val vkontakte: String,
    @ColumnInfo(name = "Web_site") val web_site: String,
    @ColumnInfo(name = "YouTube") val youTube: String)