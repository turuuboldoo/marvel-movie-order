package mn.turbo.marvel.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import mn.turbo.marvel.data.local.dao.MovieDao
import mn.turbo.marvel.data.local.dao.TvShowDao
import mn.turbo.marvel.data.local.entiry.MovieEntity
import mn.turbo.marvel.data.local.entiry.TvShowEntity

@Database(
    entities = [MovieEntity::class, TvShowEntity::class],
    version = 1
)
abstract class MarvelDatabase : RoomDatabase() {
    abstract val movieDao: MovieDao
    abstract val tvShowDao: TvShowDao
}