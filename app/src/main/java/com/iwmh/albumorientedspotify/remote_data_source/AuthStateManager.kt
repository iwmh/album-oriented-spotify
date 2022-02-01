package com.iwmh.albumorientedspotify.remote_data_source

import android.content.Intent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.iwmh.albumorientedspotify.util.Constants
import net.openid.appauth.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthStateManager @Inject constructor(
    private val localStorage: LocalStorage,
) {

    /***
     * -------------------- Fields -----------------------
     */
    // AuthState which this app updates.
    //  (You may delegate some tasks to repositories, but make sure to update this AuthState INSIDE this viewmodel file.)
    var authState by mutableStateOf(AuthState())

    // State for AppAuth (used when obtaining an authorization code)
    private var _state by mutableStateOf("")

    // AppAuth's AuthorizationService
    @Inject lateinit var authService: AuthorizationService

    /***
     * --- for "authState"
     */

    // Update AuthState in EncryptedSharedPreferences with the current AuthState state.
    fun updateAuthStateInStorage(){
        localStorage.storeData(Constants.auth_state_json, authState.jsonSerializeString())
    }

    // Update AuthState with AuthorizationResponse/AuthorizationException
    fun updateAuthStateFromAuthResponse(authResponse: AuthorizationResponse?, authException: AuthorizationException?){
        authState.update(authResponse, authException)
    }

    // Update AuthState with TokenResponse/AuthorizationException
    private fun updateAuthStateFromTokenResponse(tokenResponse: TokenResponse?, authException: AuthorizationException?){
        authState.update(tokenResponse, authException)
    }

    // Set AuthState instance when
    fun setNewAuthState(newAuthState: AuthState){
        authState = newAuthState
    }

    // See if the token is there and valid.
    fun isAuthorized(): Boolean{
        return authState.isAuthorized
    }

    // Read AuthState from SharedPreferences and set it to the field.
    fun readAuthStateStringFromSharedPreferences(): String? {
        return localStorage.readData(Constants.auth_state_json)
    }

    // Update the AuthState and EncryptedSharedPreferences.
    private fun updateAuthStateAndSharedPreferences(tokenResponse: TokenResponse?, authException: AuthorizationException?){
        // Update AuthState state.
        updateAuthStateFromTokenResponse(tokenResponse, authException)

        // Update AuthState in the EncryptedSharedPreferences.
        localStorage.storeData(Constants.auth_state_json, authState.jsonSerializeString())
    }

    /***
     * --- for "state"
     */
    // Get the "state" value.
    fun stateValue(): String {
        return _state
    }

    // Just set the new "state" value.
    fun setStateValue(newValue: String){
        _state = newValue
    }

    // Generate the "state" value.
    fun calculateStateValue(){
        val randomStringSource = "abcdefghijklmnopqrstuvwxfzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_.-~"
        var calculatedStateValue = ""
        for(x in 0 .. 127) calculatedStateValue += randomStringSource.random()
        setStateValue(calculatedStateValue)
    }

    /***
     * --- for "authService"
     */
    // Returns AuthorizationIntent.
    fun getAuthorizationRequestIntent(authorizationRequest: AuthorizationRequest): Intent {
        return authService.getAuthorizationRequestIntent(authorizationRequest)
    }

    // Exchange AuthorizationCode with AccessToken/RefreshToken.
    fun exchangeAuthorizationCode(resp: AuthorizationResponse){

        // Exchange the authorization code
        authService.performTokenRequest(
            resp.createTokenExchangeRequest()
        ) { tokenResp, tokenEx ->
            // Token exchange failed, check ex for detail.
            if (tokenResp == null) {
                throw  Exception("Token exchange failed. " + tokenEx.toString())
            }
            // Token exchange succeeded.
            // Update the AuthState
            updateAuthStateAndSharedPreferences(tokenResp, tokenEx)

            // go on...

        }
    }
}


/**
 * しないといけないのは、
 * ・AuthStateManagerに
 * ・AuthStateと
 * ・AuthorizationServiceを集めること。
 * ・initの中でtoken refreshを行うこと。
 */