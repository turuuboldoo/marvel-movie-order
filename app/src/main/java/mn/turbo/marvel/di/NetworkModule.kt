package mn.turbo.marvel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import mn.turbo.marvel.common.Constant
import mn.turbo.marvel.data.remote.MarvelApi
import mn.turbo.marvel.data.repository.MovieRepositoryImpl
import mn.turbo.marvel.data.repository.TvShowRepositoryImpl
import mn.turbo.marvel.domain.repository.MovieRepository
import mn.turbo.marvel.domain.repository.TvShowRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideMarvelApi(): MarvelApi = Retrofit
        .Builder()
        .baseUrl(Constant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(MarvelApi::class.java)

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MarvelApi
    ): MovieRepository {
        return MovieRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(
        api: MarvelApi
    ): TvShowRepository {
        return TvShowRepositoryImpl(api)
    }
}