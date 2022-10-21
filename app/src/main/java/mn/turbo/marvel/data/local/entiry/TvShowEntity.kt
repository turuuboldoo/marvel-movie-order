package mn.turbo.marvel.data.local.entiry

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "tvshows")
data class TvShowEntity(
    @SerializedName("cover_url")
    val coverUrl: String,
    @SerializedName("directed_by")
    val directedBy: String,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("last_aired_date")
    val lastAiredDate: String,
    @SerializedName("number_episodes")
    val numberEpisodes: Int,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("phase")
    val phase: Int,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("saga")
    val saga: String,
    @SerializedName("season")
    val season: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("trailer_url")
    val trailerUrl: String,
    @SerializedName("id")
    @PrimaryKey val id: Int,
)