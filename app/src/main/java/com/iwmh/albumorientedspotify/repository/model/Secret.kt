package com.iwmh.albumorientedspotify.repository.model

import kotlinx.serialization.Serializable

@Serializable
data class Secret(
    val client_id: String,
    val redirect_url: String,
)