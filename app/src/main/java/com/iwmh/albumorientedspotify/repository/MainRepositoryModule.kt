package com.iwmh.albumorientedspotify.repository

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MainRepositoryModule {

    @Provides
    @Singleton
    fun provideMainRepository(mainRepositoryImpl: MainRepositoryImpl) : MainRepository {
        return mainRepositoryImpl
    }
}