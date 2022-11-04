package mn.turbo.marvel.presenter.movie.list.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mn.turbo.marvel.domain.model.Movie

@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit = {}
) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Box(
        modifier = Modifier
            .width((screenWidth / 1.5).dp)
            .background(Color.Cyan)
            .padding(start = 16.dp)
            .clickable { onItemClick(movie) }
    ) {
        AsyncImage(
            model = movie.coverUrl,
            contentDescription = movie.title,
        )
    }
}