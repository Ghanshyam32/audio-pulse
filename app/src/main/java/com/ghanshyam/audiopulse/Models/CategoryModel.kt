package com.ghanshyam.audiopulse.Models

data class CategoryModel(
    val name: String,
    val coverUrl: String
) {
    constructor() : this("", "")
}