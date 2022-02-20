package com.iwmh.albumorientedspotify.repository.model.api

data class Profile(
    val country: String,
    val display_name: String,
    val email: String,
    val explicit_content: ExplicitContent,
    val external_urls: ExternalUrls,
    val followers: Followers,
    val href: String,
    val id: String,
    val images: List<Any>,
    val product: String,
    val type: String,
    val uri: String
)