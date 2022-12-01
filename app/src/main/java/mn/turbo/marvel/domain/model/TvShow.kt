package mn.turbo.marvel.domain.model

data class TvShow(
    val coverUrl: String? = null,
    val directedBy: String? = null,
    val id: Int,
    val imdbId: String? = null,
    val lastAiredDate: String? = null,
    val numberEpisodes: Int,
    val overview: String? = null,
    val phase: Int,
    val releaseDate: String? = null,
    val saga: String? = null,
    val season: Int,
    val title: String? = null,
    val trailerUrl: String? = null
) {
    fun getLilDesc(): String {
        return "$releaseDate | $numberEpisodes episodes"
    }
}
