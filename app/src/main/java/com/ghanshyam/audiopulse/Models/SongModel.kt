package com.ghanshyam.audiopulse.Models

class SongModel(
    val id: String,
    val title: String,
    val subtitle: String,
    val url: String,
    val coverUrl: String,
) {
    constructor() : this("", "", "", "", "")
}