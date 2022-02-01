package com.iwmh.albumorientedspotify.repository.pagingsource

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.iwmh.albumorientedspotify.remote_data_source.RemoteDataSource
import com.iwmh.albumorientedspotify.repository.model.api.Episode
import com.iwmh.albumorientedspotify.util.InjectableConstants

class EpisodesScreenPagingSource constructor(
    private val showId: String?,
    private val remoteDataSource: RemoteDataSource,
    private val injectableConstants: InjectableConstants,
) : PagingSource<String, Episode>() {

    /**
     * Remember to call refreshTokensIfNecessary() before any api call.
     */

    override fun getRefreshKey(state: PagingState<String, Episode>): String? {
        var anchor = state.anchorPosition
        return injectableConstants.baseUrl + "shows/" + showId + "/episodes";
    }

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Episode> {
        return try {
            remoteDataSource.refreshTokensIfNecessary()
            val response = remoteDataSource.getShowEpisodes(showId, params.key)
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