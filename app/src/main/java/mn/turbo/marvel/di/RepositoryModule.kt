package mn.turbo.marvel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import mn.turbo.marvel.data.local.dao.MovieDao
import mn.turbo.marvel.data.local.dao.TvShowDao
import mn.turbo.marvel.data.remote.MarvelApi
import mn.turbo.marvel.data.repository.MovieRepositoryImpl
import mn.turbo.marvel.data.repository.TvShowRepositoryImpl
import mn.turbo.marvel.domain.repository.MovieRepository
import mn.turbo.marvel.domain.repository.TvShowRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideMovieRepository(
        api: MarvelApi,
        movieDao: MovieDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): MovieRepository {
        return MovieRepositoryImpl(api, movieDao, ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideTvShowRepository(
        api: MarvelApi,
        tvShowDao: TvShowDao,
        @IoDispatcher ioDispatcher: CoroutineDispatcher
    ): TvShowRepository {
        return TvShowRepositoryImpl(api, tvShowDao, ioDispatcher)
    }
}
