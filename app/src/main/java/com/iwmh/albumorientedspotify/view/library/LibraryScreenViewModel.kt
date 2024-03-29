package com.iwmh.albumorientedspotify.view.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.pagingsource.LibraryScreenPagingSource
import com.iwmh.albumorientedspotify.util.Constants
import com.iwmh.albumorientedspotify.util.InjectableConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class LibraryScreenViewModel @Inject constructor (
    private var savedStateHandle: SavedStateHandle,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
): ViewModel() {

    // Refreshing flag for SwipeRefresh of Accompanist.
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ) {
        LibraryScreenPagingSource(remoteDataSource, injectableConstants)
    }.flow.cachedIn(viewModelScope)

    init {
        refreshShows()
    }

    // Save playlist id to use in the navigated screen.
    fun savePlaylistId(showId: String?) {
        savedStateHandle.set(Constants.nav_playlistId, showId)
    }

    private fun refreshShows() {}

}