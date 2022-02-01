package com.iwmh.albumorientedspotify.repository.model.api

data class Episode(
    val audio_preview_url: String,
    val description: String,
    val duration_ms: Int,
    val explicit: Boolean,
    val external_urls: ExternalUrls,
    val href: String,
    val html_description: String,
    val id: String,
    val images: List<Image>,
    val is_externally_hosted: Boolean,
    val is_playable: Boolean,
    val language: String,
    val languages: List<String>,
    val name: String,
    val release_date: String,
    val release_date_precision: String,
    val resume_point: ResumePoint,
    val type: String,
    val uri: String
)