package mn.turbo.marvel.domain.model

data class Movie(
    val boxOffice: String? = null,
    val chronology: Int,
    val coverUrl: String? = null,
    val directedBy: String? = null,
    val duration: Int,
    val id: Int,
    val imdbId: String? = null,
    val overview: String? = null,
    val phase: Int,
    val postCreditScenes: Int,
    val releaseDate: String? = null,
    val saga: String? = null,
    val title: String? = null,
    val trailerUrl: String? = null,
    val relatedMovie: List<RelatedMovie> = emptyList()
) {
    fun getLilDesc(): String {
        return "$releaseDate | $duration minutes"
    }
}
