package com.iwmh.albumorientedspotify.view.settings

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
import com.iwmh.albumorientedspotify.repository.pagingsource.PlaylistScreenPagingSource
import com.iwmh.albumorientedspotify.repository.pagingsource.SettingsScreenPagingSource
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.util.InjectableConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SettingsUiState(
    val userID: String = "",
    val enabledPlaylistIDs: List<String> = emptyList(),
    val loading: Boolean = false,
)

@HiltViewModel
class SettingsScreenViewModel @Inject constructor (
    private var savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
): ViewModel() {

    // UI state exposed to the UI
    private val _uiState = MutableStateFlow(SettingsUiState(loading = false))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    // Refreshing flag for SwipeRefresh of Accompanist.
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ){
        SettingsScreenPagingSource(
            remoteDataSource,
            injectableConstants,
            uiState.value.userID,
        )
    }.flow.cachedIn(viewModelScope)

    init {
        refreshAll()
    }

    fun updatePlaylistIDs(playlistIDs: List<String>){
        // store playlistIDs to SharedPreferences
        val playlistIDsString = playlistIDs.joinToString(separator = ",")
        remoteDataSource.storeData(Constants.selected_playlist_ids_comma_separated, playlistIDsString)

        _uiState.update {
            it.copy(enabledPlaylistIDs = playlistIDs)
        }
    }

    private fun refreshAll(){
        //_uiState.update { it.copy(loading = true) }

        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }

            val storedEnabledPlaylistIDs = remoteDataSource.readData(Constants.selected_playlist_ids_comma_separated)
            val storedUserID = remoteDataSource.readData(Constants.user_id)
            _uiState.update {
                it.copy(
                    loading = false,
                    enabledPlaylistIDs = if(!storedEnabledPlaylistIDs.isNullOrEmpty())
                                                storedEnabledPlaylistIDs.split(',').toMutableList()
                                         else
                                                emptyList(),
                    userID = if(storedUserID.isNullOrBlank()) "" else storedUserID
                )
            }
        }
    }




}