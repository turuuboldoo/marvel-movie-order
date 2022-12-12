package mn.turbo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import mn.turbo.marvel.data.local.entiry.MovieEntity
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
    val trailerUrl: String,
    @SerializedName("related_movies")
    val relatedMoviesDto: List<RelatedMovieDto>?,
) {
    fun toMovie(): Movie {
        return Movie(
            boxOffice = boxOffice,
            chronology = chronology,
            coverUrl = coverUrl,
            directedBy = directedBy,
            duration = duration,
            id = id,
            imdbId = imdbId,
            overview = overview,
            phase = phase,
            postCreditScenes = postCreditScenes,
            releaseDate = releaseDate,
            saga = saga,
            title = title,
            trailerUrl = trailerUrl,
            relatedMovie = relatedMoviesDto?.map {
                it.toRelatedMovie()
            }
        )
    }

    fun toMovieEntity(): MovieEntity {
        return MovieEntity(
            boxOffice = boxOffice,
            chronology = chronology,
            coverUrl = coverUrl,
            directedBy = directedBy,
            duration = duration,
            imdbId = imdbId,
            overview = overview,
            phase = phase,
            postCreditScenes = postCreditScenes,
            releaseDate = releaseDate,
            saga = saga,
            title = title,
            trailerUrl = trailerUrl,
            id = id
        )
    }
}

data class MovieList(
    val data: List<MovieDto>
)
