package com.iwmh.albumorientedspotify.view.playlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.pagingsource.PlaylistScreenPagingSource
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.util.InjectableConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class PlaylistScreenViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
): ViewModel() {

    // Refreshing flag for SwipeRefresh of Accompanist.
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    // PlaylistID for this page.
    // TODO: Needs better code here.
    var playlistID: String? = ""

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ){
        PlaylistScreenPagingSource(
//            savedStateHandle.get(Constants.nav_playlistId),
            playlistID,
            remoteDataSource,
            injectableConstants
        )
    }.flow.cachedIn(viewModelScope)

    init {
        var a: String? = savedStateHandle.get(Constants.nav_playlistId)
        var b = ""
    }
}