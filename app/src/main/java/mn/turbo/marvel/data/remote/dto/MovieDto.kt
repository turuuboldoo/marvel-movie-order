package mn.turbo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import mn.turbo.marvel.domain.model.Movie

data class MovieDto(
    @SerializedName("box_office")
    val boxOffice: String,
    @SerializedName("chronology")
    val chronology: Int,
    @SerializedName("cover_url")
    val coverUrl: String,
    @SerializedName("directed_by")
    val directedBy: String,
    @SerializedName("duration")
    val duration: Int,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("phase")
    val phase: Int,
    @SerializedName("post_credit_scenes")
    val postCreditScenes: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("saga")
    val saga: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("trailer_url")
    val trailerUrl: String
) {
    fun toMovie(): Movie {
        return Movie(
            id = id,
            title = title,
            coverUrl = coverUrl,
            duration = duration,
            directedBy = directedBy,
            releaseDate = releaseDate
        )
    }
}

data class MovieList(
    val data: List<MovieDto>
)