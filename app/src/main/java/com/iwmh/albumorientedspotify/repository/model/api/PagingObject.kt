package com.iwmh.albumorientedspotify.repository.model.api

data class PagingObject<T>(
    val href: String,
    val items: List<T>,
    val limit: Int,
    val next: String,
    val offset: Int,
    val previous: String,
    val total: Int
)