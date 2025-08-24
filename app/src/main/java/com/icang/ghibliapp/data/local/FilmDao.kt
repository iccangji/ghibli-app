package com.icang.ghibliapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FilmDao {
    @Query("SELECT * FROM films")
    suspend fun getAllFilms(): List<FilmEntity>

    @Query("SELECT * FROM films WHERE title LIKE '%' || :query || '%'")
    suspend fun filterFilms(query: String): List<FilmEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(film: List<FilmEntity>)
}