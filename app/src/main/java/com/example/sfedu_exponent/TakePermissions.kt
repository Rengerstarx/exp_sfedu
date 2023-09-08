package com.example.sfedu_exponent

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity

class TakePermissions(private val activity: Activity) {

    private val CALL_PHONE_PERMISSION_REQUEST_CODE = 1
    private var phone = "89998887766"

    fun requestPhoneCallPermission() {
        if (isCallPhonePermissionGranted()) {
            makePhoneCall()
        } else {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(android.Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION_REQUEST_CODE
            )
        }
    }

    private fun isCallPhonePermissionGranted(): Boolean {
        val permission = android.Manifest.permission.CALL_PHONE
        val granted = PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(activity, permission) == granted
    }

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

    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$phone"))
        activity.startActivity(intent)
    }

    fun changePhone(newPhone: String){
        phone=newPhone
    }

}