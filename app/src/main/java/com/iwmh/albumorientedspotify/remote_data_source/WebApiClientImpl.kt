package com.iwmh.albumorientedspotify.remote_data_source

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.iwmh.albumorientedspotify.repository.model.api.PagingObject
import com.iwmh.albumorientedspotify.repository.model.api.Playlist
import com.iwmh.albumorientedspotify.repository.model.api.Profile
import com.iwmh.albumorientedspotify.repository.model.api.TrackItem
import com.iwmh.albumorientedspotify.util.InjectableConstants
import kotlinx.coroutines.*
import okhttp3.OkHttpClient
import okhttp3.Request
import java.lang.Exception
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class WebApiClientImpl @Inject constructor(
    private val injectableConstants: InjectableConstants,
    private val authStateManager: AuthStateManager,
    private val okHttpClient: OkHttpClient,
    private val gson: Gson
) : WebApiClient {

    // Make sure to call this before every API call.
    override suspend fun refreshTokensIfNecessary(): String{
        return suspendCoroutine { continuation ->

            authStateManager.authState.performActionWithFreshTokens(
                authStateManager.authService
            ) { _, _, ex ->

                // Token refresh failed.
                if (ex != null) {
                    continuation.resumeWithException(ex)
                    throw ex
                }

                // Sync authState in storage with the updated authstate.
                authStateManager.updateAuthStateInStorage()
                continuation.resume(authStateManager.authState.accessToken!!)
            }
        }
    }

    // Get User's Playlists
    override suspend fun getUsersPlaylists(initialUrl: String?): PagingObject<Playlist> {
        val url = initialUrl ?: injectableConstants.baseUrl + "/me/playlists"

        return withContext(Dispatchers.IO) {

            refreshTokensIfNecessary()

            val response = okHttpClient.newCall(createRequest(url)).execute()
            if (!response.isSuccessful) {
                throw Exception(response.toString())
            }

            val tokenType = object : TypeToken<PagingObject<Playlist>>() {}.type
            val respString = response.body?.string()

            gson.fromJson(
                respString,
                tokenType
            )
        }
    }

    // Get Playlist's items
    override suspend fun getPlaylistItems(playlistID: String?, initialUrl: String?): PagingObject<TrackItem> {
        val url = initialUrl ?: injectableConstants.baseUrl + "/playlists/" + playlistID + "/tracks"

        return withContext(Dispatchers.IO) {

            refreshTokensIfNecessary()

            val response = okHttpClient.newCall(createRequest(url)).execute()
            if (!response.isSuccessful) {
                throw Exception(response.toString())
            }

            val tokenType = object : TypeToken<PagingObject<TrackItem>>() {}.type
            var respString = response.body?.string()

            gson.fromJson(
                respString,
                tokenType
            )
        }
    }

    // Get Current User's Profile
    override suspend fun getCurrentUsersProfile(): Profile {
        val url = injectableConstants.baseUrl + "/me"

        return withContext(Dispatchers.IO) {

            refreshTokensIfNecessary()

            val response = okHttpClient.newCall(createRequest(url)).execute()
            if (!response.isSuccessful) {
                throw Exception(response.toString())
            }

            val tokenType = object : TypeToken<Profile>() {}.type
            var respString = response.body?.string()

            gson.fromJson(
                respString,
                tokenType
            )
        }
    }

    // Get playlist's info
    override fun getPlaylistAsync(playlistID: String?): Deferred<Playlist>{
        val url = injectableConstants.baseUrl + "/playlists/" + playlistID + "?fields=name%2C%20id"

        return GlobalScope.async(Dispatchers.IO) {

            refreshTokensIfNecessary()

            val response = okHttpClient.newCall(createRequest(url)).execute()
            if (!response.isSuccessful) {
                // When request failed.
                throw Exception(response.toString())
            }

            val tokenType = object : TypeToken<Playlist>() {}.type
            var respString = response.body?.string()

            gson.fromJson(
                respString,
                tokenType
            )
        }
    }

    // Create request object
    private fun createRequest(url: String): Request {
        return Request.Builder()
            .url(url)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer ${authStateManager.authState.accessToken}")
            .build()
    }

}