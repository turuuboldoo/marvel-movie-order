package mn.turbo.marvel.data.remote

import mn.turbo.marvel.data.remote.dto.MovieDto
import mn.turbo.marvel.data.remote.dto.TvShowDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MarvelApi {
    @GET("api/v1/movies")
    suspend fun getMovies(): List<MovieDto>

    @GET("api/v1/movies/{movieId}")
    suspend fun getMovieById(@Path("movieId") movieId: Int): MovieDto

    @GET("api/v1/tvshows")
    suspend fun getTvShows(): List<TvShowDto>

    @GET("api/v1/tvshows/{tvShowId}")
    suspend fun getTvShowById(@Path("tvShowId") tvShowId: Int): TvShowDto
}
