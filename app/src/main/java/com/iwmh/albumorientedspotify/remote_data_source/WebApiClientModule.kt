package com.iwmh.albumorientedspotify.remote_data_source

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class WebApiClientModule  {

    @Provides
    @Singleton
    fun provide(webApiClientImpl: WebApiClientImpl): WebApiClient {
            return webApiClientImpl
    }
}