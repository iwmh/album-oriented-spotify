package com.iwmh.albumorientedspotify.repository.model.api

data class Owner(
    val display_name: String,
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)