package mn.turbo.marvel.data.local.entiry

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import mn.turbo.marvel.domain.model.Movie

@Entity(tableName = "movies")
data class MovieEntity(
    @SerializedName("box_office")
    val boxOffice: String? = null,

    @SerializedName("chronology")
    val chronology: Int? = null,

    @SerializedName("cover_url")
    val coverUrl: String? = null,

    @SerializedName("directed_by")
    val directedBy: String? = null,

    @SerializedName("duration")
    val duration: Int? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("phase")
    val phase: Int? = null,

    @SerializedName("post_credit_scenes")
    val postCreditScenes: Int? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("saga")
    val saga: String? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("trailer_url")
    val trailerUrl: String? = null,

    @SerializedName("id")
    @PrimaryKey val id: Int? = null,
) {
    fun toMovie(): Movie {
        return Movie(
            boxOffice = boxOffice,
            chronology = chronology ?: 0,
            coverUrl = coverUrl,
            directedBy = directedBy,
            duration = duration ?: 0,
            id = id ?: 0,
            imdbId = imdbId,
            overview = overview,
            phase = phase ?: 0,
            postCreditScenes = postCreditScenes ?: 0,
            releaseDate = releaseDate,
            saga = saga,
            title = title,
            trailerUrl = trailerUrl
        )
    }
}
