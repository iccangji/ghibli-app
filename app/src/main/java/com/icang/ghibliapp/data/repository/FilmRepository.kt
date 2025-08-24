package com.icang.ghibliapp.data.repository

import com.icang.ghibliapp.data.local.FilmDao
import com.icang.ghibliapp.data.local.FilmEntity
import com.icang.ghibliapp.data.remote.RetrofitInstance

class FilmRepository(private val dao: FilmDao) {

    suspend fun fetchFilms(): List<FilmEntity> {
        val films = RetrofitInstance.api.getAllFilms()
        dao.insertAll(films)
        return films
    }
    suspend fun getFilteredFilms(query: String): List<FilmEntity> {
        return dao.filterFilms(query)
    }

    suspend fun fetchFilmDetail(url: String): FilmEntity {
        return RetrofitInstance.api.getFilmDetail(url)
    }

    suspend fun getLocalFilms() = dao.getAllFilms()
}
