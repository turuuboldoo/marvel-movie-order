package mn.turbo.marvel.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import mn.turbo.marvel.common.Constant
import mn.turbo.marvel.data.local.MarvelDatabase
import mn.turbo.marvel.data.local.dao.MovieDao
import mn.turbo.marvel.data.local.dao.TvShowDao

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        app: Application
    ): MarvelDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            MarvelDatabase::class.java,
            Constant.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideMovieDao(
        db: MarvelDatabase
    ): MovieDao {
        return db.movieDao
    }

    @Provides
    @Singleton
    fun provideTvShowDao(
        db: MarvelDatabase
    ): TvShowDao {
        return db.tvShowDao
    }
}
