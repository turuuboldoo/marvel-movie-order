package mn.turbo.marvel.data.remote.dto

import com.google.gson.annotations.SerializedName
import mn.turbo.marvel.domain.model.RelatedMovie

data class RelatedMovieDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("cover_url")
    val coverUrl: String,
) {
    fun toRelatedMovie(): RelatedMovie {
        return RelatedMovie(
            id = id,
            title = title,
            coverUrl = coverUrl,
        )
    }
}
