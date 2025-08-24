package com.icang.ghibliapp.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.icang.ghibliapp.data.local.FilmEntity
import com.icang.ghibliapp.ui.preview.sampleFilmEntity
import com.icang.ghibliapp.viewmodel.FilmViewModel
import com.icang.ghibliapp.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: FilmViewModel, url: String, navController: NavController) {
    val film by viewModel.selectedFilmEntity.collectAsState()
    LaunchedEffect(url) {
        viewModel.loadFilmByUrl(url)
    }
    val title = if (film is UiState.Success) (film as UiState.Success<FilmEntity?>).data?.title else "Detail Film"
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (title != null) {
                        Text(title)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
            when(film){
                is UiState.Loading -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center,
                    ) {
                        CircularProgressIndicator()
                    }
                }
                is UiState.Success -> {
                    val detail = (film as UiState.Success<FilmEntity?>).data
                    Image(
                        painter = rememberAsyncImagePainter(detail?.movieBanner),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.fillMaxWidth().height(320.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    detail?.let {
                        Text(it.title, style = MaterialTheme.typography.headlineMedium)
                        Text("Director: ${it.director}", style = MaterialTheme.typography.bodyMedium)
                        Text("Release: ${it.releaseDate}", style = MaterialTheme.typography.bodyMedium)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = it.description, style = MaterialTheme.typography.bodySmall)
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

}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(
    showBackground = true,
    device = "id:pixel_4",
    showSystemUi = true
)
@Composable
fun DetailScreenPreviewDevice() {
    val detail = sampleFilmEntity
    MaterialTheme {
        val title = "Detail Film"
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(title)
                    },
                    navigationIcon = {
                        IconButton(onClick = { }) {
                            Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues).padding(16.dp)) {
                Image(
                    painter = rememberAsyncImagePainter(detail.movieBanner),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier.fillMaxWidth().height(320.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                detail.let {
                    Text(it.title, style = MaterialTheme.typography.headlineMedium)
                    Text("Director: ${it.director}", style = MaterialTheme.typography.bodyMedium)
                    Text("Release: ${it.releaseDate}", style = MaterialTheme.typography.bodyMedium)
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = it.description, style = MaterialTheme.typography.bodySmall)
                }
            }
        }
    }
}
