package mn.turbo.marvel.data.local.entiry

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import mn.turbo.marvel.domain.model.TvShow

@Entity(tableName = "tv_shows")
data class TvShowEntity(
    @SerializedName("cover_url")
    val coverUrl: String? = null,

    @SerializedName("directed_by")
    val directedBy: String? = null,

    @SerializedName("imdb_id")
    val imdbId: String? = null,

    @SerializedName("last_aired_date")
    val lastAiredDate: String? = null,

    @SerializedName("number_episodes")
    val numberEpisodes: Int? = null,

    @SerializedName("overview")
    val overview: String? = null,

    @SerializedName("phase")
    val phase: Int? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    @SerializedName("saga")
    val saga: String? = null,

    @SerializedName("season")
    val season: Int? = null,

    @SerializedName("title")
    val title: String? = null,

    @SerializedName("trailer_url")
    val trailerUrl: String? = null,

    @SerializedName("id")
    @PrimaryKey val id: Int? = null,
) {
    fun toTvShow(): TvShow {
        return TvShow(
            coverUrl = coverUrl,
            directedBy = directedBy,
            id = id ?: 0,
            imdbId = imdbId,
            lastAiredDate = lastAiredDate,
            numberEpisodes = numberEpisodes ?: 0,
            overview = overview,
            phase = phase ?: 0,
            releaseDate = releaseDate,
            saga = saga,
            season = season ?: 0,
            title = title,
            trailerUrl = trailerUrl
        )
    }
}
