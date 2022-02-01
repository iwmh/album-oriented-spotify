package com.iwmh.albumorientedspotify.repository.model.api

data class ResumePoint(
    val fully_played: Boolean,
    val resume_position_ms: Int
)