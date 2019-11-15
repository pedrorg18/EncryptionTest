package com.pedroroig.encryptiontest

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toast.LENGTH_LONG
import com.pedroroig.encryptiontest.encryption.SecureSharedPreferences
import java.util.*

class MainActivity : AppCompatActivity() {

    private val secretMessageKey = "SECRET_MESSAGE_KEY"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val initTime = Date().time
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val securePrefs = SecureSharedPreferences.getInstance(this)
            val textToEncrypt = "Hello, this is a secret message"
            println("before: $textToEncrypt")
            securePrefs.saveStringPref(secretMessageKey, textToEncrypt)
            println("after save - TIME:: ${Date().time - initTime}")
            val result = securePrefs.getStringPref(secretMessageKey)
            println("after: $result  - TIME:: ${Date().time - initTime}")
        } else {
            Toast.makeText(this, "Sorry, this only works with APIs equal or greater than 23 (yours is ${Build.VERSION.SDK_INT})", LENGTH_LONG).show()
        }

    }
}
