package com.example.sfeduexpver20.startpage.phone

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

/**
 * Класс для создания объекта телефон и работе с ним
 * @author Rengerstar <vip.bekezin@mail.ru>
 * */
class Phone{

    /**
     * @param fragment - фрагмент откуда будет происходить вызов
     * @param number - номер телефона
     * Так же создается объект класса по проверке раазрешений на вызов
     * */
    constructor(fragment: Fragment, number: String){
        this.fragment = fragment
        this.number = number
        callPermissionHandler = CallPermissionHandler(fragment)
    }

    private val fragment: Fragment
    private var number: String
    private val callPermissionHandler: CallPermissionHandler

    /**
     * Звонок на переданный номер телефона после проверки разрешений и в случаи его отсутсвия, его запроса
     * */
    fun makeCall(){
        if(callPermissionHandler.checkCallPermission()){
            val intent = Intent(Intent.ACTION_CALL)
            intent.data = Uri.parse(number)
            fragment.startActivity(intent)
        }
        else
            callPermissionHandler.requestCallPermission()
    }

    /**
     * Смена номера
     * @param number -  новый номер
     * */
    fun changeNumber(number: String){
        this.number=number
    }

    /**
     * Метод передающий выбор пользователя дальше в класс CallPermissionHandler
     * */
    fun takePerm(requestCode: Int, permissions: Array<String>, grantResults: IntArray){
        callPermissionHandler.onRequestPermissionsResult(requestCode, permissions, grantResults, number)
    }

}