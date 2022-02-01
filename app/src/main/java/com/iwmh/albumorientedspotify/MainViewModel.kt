package com.iwmh.albumorientedspotify

import android.content.Intent
import androidx.lifecycle.ViewModel
import com.iwmh.albumorientedspotify.remote_data_source.AuthStateManager
import dagger.hilt.android.lifecycle.HiltViewModel
import net.openid.appauth.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authStateManager: AuthStateManager,
): ViewModel(){

    /***
     * --- for managing "authState".
     */
    // Update AuthState with AuthorizationResponse/AuthorizationException
    fun updateAuthStateFromAuthResponse(authResponse: AuthorizationResponse?, authException: AuthorizationException?){
        authStateManager.updateAuthStateFromAuthResponse(authResponse, authException)
    }

    // Set AuthState instance when
    fun setNewAuthState(newAuthState: AuthState){
        authStateManager.setNewAuthState(newAuthState)
    }

    // See if the token is there and valid.
    fun isAuthorized(): Boolean{
        return authStateManager.isAuthorized()
    }

    // Read AuthState from SharedPreferences and set it to the field.
    fun readAuthStateStringFromSharedPreferences(): String? {
        return authStateManager.readAuthStateStringFromSharedPreferences()
    }

    /***
     * --- for managing "state" (which is used in PKCE authorization flow).
     */
    // Get the "state" value.
    fun stateValue(): String {
        return authStateManager.stateValue()
    }

    // Just set the new "state" value.
    fun setStateValue(newValue: String){
        authStateManager.setStateValue(newValue)
    }

    // Generate the "state" value.
    fun calculateStateValue(){
        authStateManager.calculateStateValue()
    }

    /***
     * --- for managing "authService".
     */
    // Returns AuthorizationIntent.
    fun getAuthorizationRequestIntent(authorizationRequest: AuthorizationRequest): Intent {
        return authStateManager.getAuthorizationRequestIntent(authorizationRequest)
    }

    // Exchange AuthorizationCode with AccessToken/RefreshToken.
    fun exchangeAuthorizationCode(resp: AuthorizationResponse){
        authStateManager.exchangeAuthorizationCode(resp)
    }

}
