package com.anthony.wu.my.git.dto.response

data class BasicTokenDto(
    val app: App,
    val created_at: String,
    val fingerprint: Any,
    val hashed_token: String,
    val id: Int,
    val note: String,
    val note_url: Any,
    val scopes: List<String>,
    val token: String,
    val token_last_eight: String,
    val updated_at: String,
    val url: String
)

data class App(
    val client_id: String,
    val name: String,
    val url: String
)