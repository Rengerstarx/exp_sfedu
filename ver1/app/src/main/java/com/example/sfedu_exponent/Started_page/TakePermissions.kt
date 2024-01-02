package com.example.sfedu_exponent.Started_page

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class TakePermissions(private val activity: Activity) {

    /**Код для получения разрешения на звонки*/
    private val CALL_PHONE_PERMISSION_REQUEST_CODE = 1
    private var phone = "89998887766" //Номер для звонка

    fun requestPhoneCallPermission() {
        if (isCallPhonePermissionGranted()) { //Если норм то звоним
            makePhoneCall()
        } else { //Иначе запрашиваем разрешение
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION_REQUEST_CODE
            )
        }
    }

    /**Запрос в манифест на разрешение о звонке*/
    private fun isCallPhonePermissionGranted(): Boolean {
        val permission = android.Manifest.permission.CALL_PHONE
        val granted = PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(activity, permission) == granted
    }

    /**Получение разрешение с проверкое дано ли оно, и если все норм то звоним*/
    fun onRequestPermissionsResult(requestCode: Int, grantResults: IntArray) {
        when (requestCode) {
            CALL_PHONE_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    makePhoneCall()
                } else {
                    // Обработка отказа в предоставлении разрешения
                }
            }
        }
    }

    /**Звонок*/
    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
        activity.startActivity(intent)
    }

    /**Смена номера для звонка*/
    fun changePhone(newPhone: String){
        phone=newPhone
    }

}