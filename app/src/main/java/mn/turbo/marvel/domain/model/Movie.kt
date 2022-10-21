package mn.turbo.marvel.domain.model

data class Movie(
    val id: Int,
    val title: String,
    val coverUrl: String,
    val duration: Int,
    val directedBy: String,
    val releaseDate: String,
)
