package com.diegolima.codehero.data.network.MarvelApi.model

data class Character(
    val id: Int,
    val name: String,
    val description: String,
    val thumbnail: Thumbnail
)
