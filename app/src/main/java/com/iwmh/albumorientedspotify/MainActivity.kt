package com.iwmh.albumorientedspotify

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.iwmh.albumorientedspotify.repository.model.Secret
import com.iwmh.albumorientedspotify.util.Util
import com.iwmh.albumorientedspotify.view.auth.AuthScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import net.openid.appauth.*

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    // Viewmodel for this activity.
    private val mainViewModel: MainViewModel by viewModels()

    // launcher to launch the activity for login screen.
    private val launcher: ActivityResultLauncher<Intent> =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->

            // If the user canceled, then return immediately
            if(activityResult.resultCode != Activity.RESULT_OK){
                return@registerForActivityResult
            }

            // Extract the authorization response and exception.
            val data = activityResult.data
            val resp = AuthorizationResponse.fromIntent(data!!)
            val ex = AuthorizationException.fromIntent(data)

            if(resp == null){
                Log.e("AuthorizationActivity", ex.toString())
                throw ex!!
            }

            // update the AuthState
            mainViewModel.updateAuthStateFromAuthResponse(resp, ex)

            // Check if the "state" matches precalculated state value.
            if(resp.state != mainViewModel.stateValue()){
                throw  Exception("Detected mismatch with state value you calculated. " + ex.toString())
            }

            // Clear the state after confirming you received the expected state.
            mainViewModel.setStateValue("")

            MainScope().launch {

                // Show a toast
                // ...showing a indicator is a better option.
                Toast.makeText(applicationContext, "Please wait a minute ...", Toast.LENGTH_LONG).show()

                // Exchange the authorization code
                val result = mainViewModel.exchangeAuthorizationCode(resp)
                if(result == ""){
                    // Shows the main screen.
                    setContent {
                        AlbumOrientedSpotifyAppScreen(viewModel = mainViewModel)
                    }
                } else {
                    Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Read AuthState from SharedPreferences and set it the viewmodel.
        var stateJson = mainViewModel.readAuthStateStringFromSharedPreferences()

        val serviceConfig = AuthorizationServiceConfiguration(
            Uri.parse("https://accounts.spotify.com/authorize"),  // authorization endpoint
            Uri.parse("https://accounts.spotify.com/api/token"),  // token endpoint
        )

        if (!stateJson.isNullOrEmpty()){
            // AuthState successfully read from SharedPreferences.
            // Set it in the viewmodel.
            mainViewModel.setNewAuthState(AuthState.jsonDeserialize(stateJson))
        } else {
            // AuthState isn't stored in SharedPreferences, so the user hasn't been logged in.
            // Set the initial AuthState in the viewmodel.
            mainViewModel.setNewAuthState(AuthState(serviceConfig))
        }

        // Check if the AuthState is valid
        if(mainViewModel.isAuthorized()){
            // If the user's already logged in, show the home page.
            setContent {
                AlbumOrientedSpotifyAppScreen(viewModel = mainViewModel)
            }
        } else {
            // Otherwise, show the login page.

            // ---------- ↓ AppAuth Preparation ↓ ----------
            // prepare for PKCE info
            val codeVerifier = CodeVerifierUtil.generateRandomCodeVerifier()
            val codeChallenge = CodeVerifierUtil.deriveCodeVerifierChallenge(codeVerifier)

            // Calculate the state value
            mainViewModel.calculateStateValue()

            // get secret.json file from "assets" folder
            val secretString = Util.loadJSONFromAsset(this, "secret.json")

            // deserialize the json string to Secret data class
            val secretData = Json.decodeFromString<Secret>(secretString ?: "")

            val authRequest = AuthorizationRequest.Builder(
                serviceConfig,                      // the authorization service configuration
                secretData.client_id,               // the client ID, typically pre-registered and static
                ResponseTypeValues.CODE,            // the response_type value: we want a code
                Uri.parse(secretData.redirect_url)  // the redirect URI to which the auth response is sent
            ).setScope(                             // the scopes
                        "playlist-modify-private" + " " +
                        "playlist-read-private" + " " +
                        "playlist-modify-public" + " " +
                        "playlist-read-collaborative" + " " +
                        "user-read-private" + " " +
                        "user-read-playback-state" + " " +
                        "user-modify-playback-state" + " " +
                        "user-read-currently-playing" + " " +
                        "user-library-modify" + " " +
                        "user-library-read" + " " +
                        "app-remote-control" + " " +
                        "streaming" + " "
            ).setCodeVerifier(
                codeVerifier,
                codeChallenge,
                CodeVerifierUtil.getCodeVerifierChallengeMethod()
            ).setState(
                mainViewModel.stateValue()
            ).build()
            // ---------- ↑ AppAuth Preparation ↑ ----------

            val authIntent = mainViewModel.getAuthorizationRequestIntent(authRequest)

            setContent {
                AuthScreen(
                    authIntent = authIntent,
                    launcher = launcher
                )
            }
        }
    }
}
