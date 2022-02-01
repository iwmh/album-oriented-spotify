package com.iwmh.albumorientedspotify.repository

interface MainRepository {

    fun storeData(keyString: String, valueString: String)

    fun readData(keyString: String): String?

    // Make sure to call this before every API call.
    suspend fun refreshTokensIfNecessary(): String

}
