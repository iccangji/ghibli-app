package com.icang.ghibliapp.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.icang.ghibliapp.data.local.FilmEntity
import com.icang.ghibliapp.data.repository.FilmRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}
class FilmViewModel(private val repository: FilmRepository) : ViewModel() {

    private val _films = MutableStateFlow<UiState<List<FilmEntity>>>(UiState.Loading)
    val films: StateFlow<UiState<List<FilmEntity>>> = _films

    private val _selectedFilmEntity = MutableStateFlow<UiState<FilmEntity?>>(UiState.Loading)
    val selectedFilmEntity: StateFlow<UiState<FilmEntity?>> = _selectedFilmEntity

    var searchText by mutableStateOf("")
        private set

    fun updateSearchText(text: String) {
        searchText = text
        filterFilms(text)
    }

    private fun filterFilms(query: String) {
        viewModelScope.launch {
            val result = repository.getFilteredFilms(query)
            _films.value = UiState.Success(result)
        }
    }

    fun loadFilmByUrl(url: String) {
        if(url.isNotEmpty()){
            viewModelScope.launch {
                try {
                    val detail = repository.fetchFilmDetail(url)
                    _selectedFilmEntity.value = UiState.Success(detail)
                }catch (e: Exception){
                    _selectedFilmEntity.value = UiState.Error(e.toString())
                }
            }
        }
    }
    fun loadFilms() {
        viewModelScope.launch {
            try {
                val remoteData = repository.fetchFilms()
                _films.value = UiState.Success(remoteData)
            } catch (e: Exception) {
                try {
                    val localData = repository.getLocalFilms()
                    _films.value = UiState.Success(localData)
                }catch (e: Exception){
                    _films.value = UiState.Error(e.toString())
                }
            }
        }
    }
}
