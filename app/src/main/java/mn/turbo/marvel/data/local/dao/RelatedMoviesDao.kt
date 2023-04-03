package mn.turbo.marvel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import mn.turbo.marvel.data.local.entiry.RelatedMoviesEntity

@Dao
interface RelatedMoviesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRelatedMovie(relatedMoviesEntity: RelatedMoviesEntity)
}
