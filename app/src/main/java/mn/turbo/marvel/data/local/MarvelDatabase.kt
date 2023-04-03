package mn.turbo.marvel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import mn.turbo.marvel.data.local.dao.MovieDao
import mn.turbo.marvel.data.local.dao.RelatedMoviesDao
import mn.turbo.marvel.data.local.dao.TvShowDao
import mn.turbo.marvel.data.local.entiry.MovieEntity
import mn.turbo.marvel.data.local.entiry.RelatedMoviesEntity
import mn.turbo.marvel.data.local.entiry.TvShowEntity

@Database(
    entities = [
        MovieEntity::class,
        TvShowEntity::class,
        RelatedMoviesEntity::class
    ],
    version = 1
)
@TypeConverters(DataConverter::class)
abstract class MarvelDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val tvShowDao: TvShowDao
    abstract val relatedMoviesDao: RelatedMoviesDao
}
