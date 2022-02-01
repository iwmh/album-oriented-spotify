package com.iwmh.albumorientedspotify.view.library

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.pagingsource.LibraryScreenPagingSource
import com.iwmh.albumorientedspotify.repository.model.api.ItemShow
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

//    private val uiState = MutableStateFlow(LibraryViewUiState(isLoading = true))

    // Refreshing flag for SwipeRefresh of Accompanist.
    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean>
        get() = _isRefreshing.asStateFlow()

    var pagingFlow = Pager(
        PagingConfig(pageSize = 20)
    ){
        LibraryScreenPagingSource(remoteDataSource, injectableConstants)
    }.flow.cachedIn(viewModelScope)

//    val uiState = viewModelState
//        .map { it.toUiState() }
//        .stateIn(
//            viewModelScope,
//            SharingStarted.Eagerly,
//            viewModelState.value.toUiState()
//        )

    init {
        refreshShows()
    }

    fun saveShowId(showId: String?){
        savedStateHandle.set(Constants.nav_showId, showId)
    }

    private fun refreshShows(){
/*
        val authExceptionHandler = CoroutineExceptionHandler{ _, exception ->
            // TODO: Unconfirmed that the AuthenticationException is caught here.
            // TODO: Navigate the user to login again.
            uiState.update {
                it.copy(isLoading = false, errorMessages = listOf("An error occurred. Please try again."))
            }
        }

        uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch(authExceptionHandler) {
            mainRepository.refreshTokensIfNecessary()
//            var result = mainRepository.getUsersSavedShows()

            uiState.update {
                it.copy(isLoading = false, followingShows = result.items))
            }
        }
*/


//        try {
//            viewModelScope.launch {
//                mainRepository.refreshTokensIfNecessary()
//                var result = mainRepository.getUsersSavedShows()
//                viewModelState.update {
//                    it.copy(isLoading = false, showsFeed = ShowsFeed(followingShows = result.items))
//                }
//            }
//        } catch (e: AuthorizationException){
//            // TODO: Navigate and let the user to login again.
//            var a = ""
//        } catch (e: Exception){
//            // TODO: 'Error occurred. Please try again.'
//            viewModelState.update {
//                it.copy(isLoading = false, errorMessages = listOf("An error occurred. Please try again."))
//            }
//        }
//    }
}

//sealed interface LibraryViewUiState {
//    val isLoading: Boolean
//    val errorMessages: List<String>
//    data class NoShows(
//        override  val isLoading: Boolean,
//        override val errorMessages: List<String>,
//    ): LibraryViewUiState
//
//    data class HasShows(
//        val followingShows: List<ItemShow>,
//        override  val isLoading: Boolean,
//        override val errorMessages: List<String>,
//    ): LibraryViewUiState
//}

data class LibraryViewUiState (
    val isLoading: Boolean,
    val errorMessages: List<String>,
    val followingShows: List<ItemShow>,
)


//private data class LibraryViewModelState(
//    val showsFeed: ShowsFeed = ShowsFeed(followingShows = emptyList()),
//    val isLoading: Boolean = false,
//    val errorMessages: List<String> = emptyList(),
//){
//    fun toUiState(): LibraryViewUiState =
//            LibraryViewUiState(
//                isLoading = isLoading,
//                errorMessages = errorMessages,
//                followingShows = showsFeed.followingShows
//            )

//    fun toUiState(): LibraryViewUiState =
//        if (showsFeed == null){
//            LibraryViewUiState.NoShows(
//                isLoading = isLoading,
//                errorMessages = errorMessages
//            )
//        } else {
//            LibraryViewUiState.HasShows(
//                isLoading = isLoading,
//                errorMessages = errorMessages,
//                followingShows = showsFeed.followingShows
//            )
//        }

}