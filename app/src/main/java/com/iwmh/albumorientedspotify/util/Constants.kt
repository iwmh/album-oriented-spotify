package com.iwmh.albumorientedspotify.util

class Constants {
    companion object {
        const val shared_prefs_file = "albumorientedspotify_encrypted_prefs"
        const val auth_state_json = "authstate_json"

        // For
        // ・navigation's argument name
        // ・SavedStateHandle's key you use between destinations.
        const val nav_playlistId = "playlistId"

        const val user_id = "userID"
        const val selected_playlist_ids_comma_separated = "selectedPlaylistIDsCommaSeparated"

    const val nav_episodeName = "episodeName"
        const val nav_imageUrl = "imageUrl"
        const val nav_description = "description"
        const val nav_duration = "duration"
        const val nav_releaseDate = "releaseDate"
    }
}