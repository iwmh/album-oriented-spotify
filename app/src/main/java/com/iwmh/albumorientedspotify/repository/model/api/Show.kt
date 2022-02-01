package com.iwmh.albumorientedspotify.repository.model.api

import com.iwmh.albumorientedspotify.repository.model.api.ExternalUrls
import com.iwmh.albumorientedspotify.repository.model.api.Image

data class Show(
    val available_markets: List<String>,
    val copyrights: List<Any>,
    val description: String,
    val explicit: Boolean,
    val external_urls: ExternalUrls,
    val href: String,
    val html_description: String,
    val id: String,
    val images: List<Image>,
    val is_externally_hosted: Boolean,
    val languages: List<String>,
    val media_type: String,
    val name: String,
    val publisher: String,
    val total_episodes: Int,
    val type: String,
    val uri: String
)