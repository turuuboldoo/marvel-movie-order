package mn.turbo.marvel.presenter.movie.list.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import mn.turbo.marvel.domain.model.Movie

@Composable
fun MovieListItem(
    movie: Movie,
    onItemClick: (Movie) -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { onItemClick(movie) }
    ) {
        AsyncImage(
            model = movie.coverUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        )
    }
}
