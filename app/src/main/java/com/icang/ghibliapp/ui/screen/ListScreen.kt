package com.icang.ghibliapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import coil.compose.rememberAsyncImagePainter
import com.icang.ghibliapp.data.local.FilmEntity
import com.icang.ghibliapp.ui.preview.sampleFilmEntity
import com.icang.ghibliapp.viewmodel.FilmViewModel
import com.icang.ghibliapp.viewmodel.UiState
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ListScreen(viewModel: FilmViewModel, onItemClick: (FilmEntity) -> Unit) {
    val films by viewModel.films.collectAsState()
    var searchText by remember { mutableStateOf(viewModel.searchText) }
    val gridState = rememberLazyGridState()
    val focusManager = LocalFocusManager.current
    LaunchedEffect(gridState) {
        snapshotFlow { gridState.isScrollInProgress }
            .collectLatest { isScrolling ->
                if (isScrolling) {
                    focusManager.clearFocus()
                }
            }
    }
    Column {
        Text(
            text = "Ghibli Film App",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )
        TextField(
            value = searchText,
            onValueChange = { newText ->
                searchText = newText
                viewModel.updateSearchText(newText)
            },
            placeholder = { Text("Cari film...") },
            trailingIcon = {
                if (searchText.isNotEmpty()) {
                    IconButton(onClick = {
                        searchText = ""
                        viewModel.updateSearchText("")
                    }) {
                        Icon(Icons.Filled.Clear, contentDescription = "Clear text")
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
        when(films){
            is UiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(top = 24.dp),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items((films as UiState.Success<List<FilmEntity>>).data) { film ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onItemClick(film) }
                                .height(220.dp)
                        ) {
                            Column(modifier = Modifier.padding(8.dp)) {
                                Image(
                                    painter = rememberAsyncImagePainter(film.movieBanner),
                                    contentDescription = null,
                                    contentScale = ContentScale.FillHeight,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(160.dp)
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(text = film.title, lineHeight = 1.em,style = MaterialTheme.typography.titleMedium)
                            }
                        }
                    }
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text("Terjadi Kesalahan", textAlign = TextAlign.Center)
                }
            }
        }
    }
}



@Composable
fun ListScreenPreviewContent(onItemClick: (FilmEntity) -> Unit = {}) {
    val films = listOf(sampleFilmEntity, sampleFilmEntity.copy(id = "2", title = "Spirited Away"))
    Column {
        Text(
            text = "Ghibli Film App",
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
        )
        TextField(
            value = "Cari...",
            onValueChange = { },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(films) { film ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onItemClick(film) }
                        .height(220.dp)
                ) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Image(
                            painter = rememberAsyncImagePainter(film.movieBanner),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = film.title, style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}

@Preview(
    showBackground = true,
    device = "id:pixel_4",
    showSystemUi = true
)
@Composable
fun ListScreenPreview() {
    MaterialTheme {
        ListScreenPreviewContent()
    }
}