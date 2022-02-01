package com.iwmh.albumorientedspotify.remote_data_source

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GsonModule @Inject constructor(
) {
    @Provides
    @Singleton
    fun provide(): Gson{
        return Gson()
    }
}