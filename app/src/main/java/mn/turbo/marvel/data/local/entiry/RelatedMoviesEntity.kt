package mn.turbo.marvel.data.local.entiry

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "related_movies")
data class RelatedMoviesEntity(
    @SerializedName("related_movie_id")
    val relatedMovies: List<Int>? = null,

    @SerializedName("movie_id")
    @PrimaryKey val movieId: Int? = null,
)
