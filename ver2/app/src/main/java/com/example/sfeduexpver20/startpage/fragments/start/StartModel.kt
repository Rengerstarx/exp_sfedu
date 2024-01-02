package com.example.sfeduexpver20.startpage.fragments.start

import android.content.res.Resources
import com.example.sfeduexpver20.R

class StartModel(private val resources: Resources) {

    private val textSlogan = resources.getString(R.string.textSlogan)
    private val textLable = resources.getString(R.string.exp_sfedu)

    fun takeLable(): String{
        return textSlogan
    }

}