package com.iwmh.albumorientedspotify.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.model.api.ItemShow
import com.iwmh.albumorientedspotify.util.InjectableConstants

class LibraryScreenPagingSource constructor(
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
) : PagingSource<String, ItemShow>() {

    /**
     * Remember to call refreshTokensIfNecessary() before any api call.
     */

    override fun getRefreshKey(state: PagingState<String, ItemShow>): String? {
        var anchor = state.anchorPosition
        return injectableConstants.baseUrl + "/me/shows";
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, ItemShow> {
        return try {
            remoteDataSource.refreshTokensIfNecessary()
            val response = remoteDataSource.getUsersSavedShows(params.key)
            LoadResult.Page(
                data = response.items,
                nextKey = response.next,
                prevKey = response.previous
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}