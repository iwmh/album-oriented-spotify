package com.iwmh.albumorientedspotify.remote_data_source

import android.content.SharedPreferences
import javax.inject.Inject

class LocalKeyValueStorageImpl @Inject constructor(
    private val encryptedSharedPreferences: SharedPreferences
) : LocalStorage {
    override fun storeData(keyString: String, valueString: String) {
        encryptedSharedPreferences.edit()
            .putString(keyString, valueString)
            .apply()
    }

    override fun readData(keyString: String): String? {
        return encryptedSharedPreferences.getString(keyString, null)
    }
}