package com.iwmh.albumorientedspotify.view.episodes

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.pagingsource.EpisodesScreenPagingSource
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.util.InjectableConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class EpisodesScreenViewModel @Inject constructor (
    savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
): ViewModel() {

    // Refreshing flag for SwipeRefresh of Accompanist.
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    // ShowID for this page.
    // TODO: Needs better code here.
    var showId: String? = ""

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ){
        EpisodesScreenPagingSource(
//            savedStateHandle.get(Constants.nav_playlistId),
            showId,
            remoteDataSource,
            injectableConstants
        )
    }.flow.cachedIn(viewModelScope)

    init {
        var a: String? = savedStateHandle.get(Constants.nav_playlistId)
        var b = ""
    }
}