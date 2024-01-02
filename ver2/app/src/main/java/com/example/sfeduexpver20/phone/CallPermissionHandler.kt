package com.example.sfeduexpver20.startpage.phone

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

/**
 * Класс для проверки и выдачи permissions на вызов по номеру телефона
 * @author Rengerstar <vip.bekezin@mail.ru>
 * */

/**
 * @param fragment - фрагмент в котором происходит запрос разрешения
 * */
class CallPermissionHandler(private val fragment: Fragment) {
    private val CALL_PERMISSION_REQUEST_CODE = 1

    /**
     * Проверка на то, выдано ли разрешение на звонок
     * @return Данно ли разрешние или нет
     * */
    fun checkCallPermission(): Boolean {
        val permission = Manifest.permission.CALL_PHONE
        val granted = PackageManager.PERMISSION_GRANTED
        return ContextCompat.checkSelfPermission(fragment.requireContext(), permission) == granted
    }

    /**
     * Вызов разрешений
     * */
    fun requestCallPermission() {
        val permission = Manifest.permission.CALL_PHONE
        ActivityCompat.requestPermissions(fragment.requireActivity(), arrayOf(permission), CALL_PERMISSION_REQUEST_CODE)
    }

    /**
     * Результат получения разрешений
     * @param requestCode - код запроса
     * @param permissions - не используется, является побочной переменной при передаче из исходного метода
     * @param grantResults
     * @param number - номер, что бы в случае положительного результата сразу набрать номер
     * */
    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray, number: String) {
        if (requestCode == CALL_PERMISSION_REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse(number)
                fragment.startActivity(intent)
            }
        }
    }
}
