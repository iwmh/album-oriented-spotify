package com.iwmh.albumorientedspotify.repository

import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import javax.inject.Inject

class MainRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource
) : MainRepository {

    // Store data.
    override fun storeData(keyString: String, valueString: String){
        remoteDataSource.storeData(keyString, valueString)
    }

    // Read data.
    override fun readData(keyString: String): String?{
        return remoteDataSource.readData(keyString)
    }

    override suspend fun refreshTokensIfNecessary(): String {
        return remoteDataSource.refreshTokensIfNecessary()
    }

}