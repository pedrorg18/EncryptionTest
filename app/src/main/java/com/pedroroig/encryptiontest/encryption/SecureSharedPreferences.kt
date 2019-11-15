package com.pedroroig.encryptiontest.encryption

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys


@RequiresApi(Build.VERSION_CODES.M)
class SecureSharedPreferences(ctx: Context) {

    companion object {
        private var instance: SecureSharedPreferences? = null
        fun getInstance(ctx: Context): SecureSharedPreferences {
            if(instance == null)
                instance = SecureSharedPreferences(ctx)
            return instance!!
        }
    }

    private val encryptedPreferences = EncryptedSharedPreferences.create(
        "secure_shared_prefs",
        getOrCreateAlias(),
        ctx,
        EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
        EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
    )

    fun getStringPref(key: String) = encryptedPreferences.getString(key, "")!!

    fun saveStringPref(key: String, token: String) {
        with(encryptedPreferences.edit()) {
            putString(key, token)
            apply()
        }
    }

    private fun getOrCreateAlias(): String {
        val keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC
        return MasterKeys.getOrCreate(keyGenParameterSpec)
    }

}