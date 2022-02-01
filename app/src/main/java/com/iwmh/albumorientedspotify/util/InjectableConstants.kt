package com.iwmh.albumorientedspotify.util

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

data class InjectableConstants(
    val baseUrl: String
)

@Module
@InstallIn(SingletonComponent::class)
class InjectableConstantsModule{

    @Provides
    @Singleton
    fun provide(): InjectableConstants {
        return InjectableConstants(
            baseUrl =  "https://api.spotify.com/v1"
        )
    }

}