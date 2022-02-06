package com.iwmh.albumorientedspotify.repository.model.api

data class AddedBy(
    val external_urls: ExternalUrls,
    val href: String,
    val id: String,
    val type: String,
    val uri: String
)