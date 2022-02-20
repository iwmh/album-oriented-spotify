package com.iwmh.albumorientedspotify.view.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.model.api.Playlist
import com.iwmh.albumorientedspotify.repository.model.api.Profile
import com.iwmh.albumorientedspotify.repository.pagingsource.LibraryScreenPagingSource
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.util.InjectableConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class HomeUiState(
    val userID: String = "",
    val loading: Boolean = false,
)

@HiltViewModel
class HomeScreenViewModel @Inject constructor (
    private var savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
): ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        refreshAll()
    }

    private fun refreshAll(){
        _uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            val storedUserID = remoteDataSource.readData(Constants.user_id)
            if(!storedUserID.isNullOrEmpty()){
                _uiState.update {
                    it.copy(
                        loading = false,
                        userID = storedUserID,
                    )
                }
            } else {

                // Trigger repository requests in parallel
                val profileDeferred = async { remoteDataSource.getCurrentUsersProfile() }

                // Wait for all requests to finish
                val profile = profileDeferred.await()

                // store data to sharedpreferences.
                remoteDataSource.storeData(Constants.user_id, profile.id)

                _uiState.update {
                    it.copy(
                        loading = false,
                        userID = profile.id,
                    )
                }

            }
        }
    }




}