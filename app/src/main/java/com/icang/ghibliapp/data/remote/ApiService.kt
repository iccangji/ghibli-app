package com.icang.ghibliapp.data.remote

import com.icang.ghibliapp.data.local.FilmEntity
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET("/films")
    suspend fun getAllFilms(): List<FilmEntity>
    @GET
    suspend fun getFilmDetail(@Url url: String): FilmEntity
}