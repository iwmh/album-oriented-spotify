package com.iwmh.albumorientedspotify

import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.iwmh.albumorientedspotify.util.Util
import androidx.test.filters.SmallTest
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.iwmh.albumorientedspotify.remote_data_source.AuthStateManager
import com.iwmh.albumorientedspotify.remote_data_source.LocalKeyValueStorageImpl
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSourceImpl
import com.iwmh.albumorientedspotify.remote_data_source.WebApiClientImpl
import com.iwmh.albumorientedspotify.repository.MainRepository
import com.iwmh.albumorientedspotify.repository.MainRepositoryImpl
import com.iwmh.albumorientedspotify.repository.model.Secret
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.util.InjectableConstants
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.*
import org.junit.Assert.*
import java.io.IOException

@SmallTest
class InstrumentedUnitTest{

    companion object {
        lateinit var localKeyValueStorageImpl: LocalKeyValueStorageImpl
        lateinit var remoteDataSourceImpl: RemoteDataSourceImpl
        lateinit var mainRepository: MainRepository

        lateinit var mockWebServer: MockWebServer

        @BeforeClass
        @JvmStatic
        fun init() {
            // Context of the app under test.
            val context = InstrumentationRegistry.getInstrumentation().targetContext

            val mainKey = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()

            val prefs = EncryptedSharedPreferences.create(
                context,
                Constants.shared_prefs_file,
                mainKey,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )

            // Init for storage, remotedatasource and repository.
            val okHttpClient = OkHttpClient()
            localKeyValueStorageImpl = LocalKeyValueStorageImpl(prefs)
            val authStateManager = AuthStateManager(localKeyValueStorageImpl)
            authStateManager.authService = AuthorizationService(context)
            val gson = Gson()
            val injectableConstants = InjectableConstants(
                baseUrl = "http://localhost:8080"
            )
            val webApiClientImpl = WebApiClientImpl(injectableConstants ,authStateManager, okHttpClient, gson)

            remoteDataSourceImpl = RemoteDataSourceImpl(webApiClientImpl, LocalKeyValueStorageImpl(prefs))

            mainRepository = MainRepositoryImpl(remoteDataSourceImpl)

            // mock web server

            mockWebServer = MockWebServer()
            mockWebServer.start(8080)
        }

        @AfterClass
        @JvmStatic
        fun end(){
            mockWebServer.shutdown()
        }

    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun loadJsonFromAsset_test(){

        // Context of the app under test.
        val context = InstrumentationRegistry.getInstrumentation().targetContext

        // get secret_test.json file from "assets" folder
        val secretString = Util.loadJSONFromAsset(context, "secret_test.json")

        // deserialize the json string to Secret data class
        val secretData = Json.decodeFromString<Secret>(secretString ?: "")

        assertEquals( "test_client_id", secretData.client_id)
        assertEquals( "test_redirect_url", secretData.redirect_url)
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun loadJsonFromAsset_test_no_file_found(){

        assertThrows(IOException::class.java) {

            // Context of the app under test.
            val context = InstrumentationRegistry.getInstrumentation().targetContext

            // get secret_test.json file from "assets" folder
            val secretString = Util.loadJSONFromAsset(context, "secret_test_.json")

        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    @Test
    fun read_write_encrypted_shared_preferences(){

        val testValue = "test string"

        // Store data.
        mainRepository.storeData(Constants.shared_prefs_file, testValue)

        // Read data.
        val readValue = mainRepository.readData(Constants.shared_prefs_file)

        assertEquals(testValue, readValue)
    }





}