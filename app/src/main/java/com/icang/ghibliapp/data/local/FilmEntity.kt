package com.icang.ghibliapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "films")
data class FilmEntity(
    @PrimaryKey val id: String,
    val title: String,
    val description: String,
    val director: String,
    @SerializedName("release_date") val releaseDate: String,
    @SerializedName("movie_banner") val movieBanner: String,
    val url: String,
)

