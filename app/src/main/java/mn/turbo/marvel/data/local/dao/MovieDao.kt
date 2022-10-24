package mn.turbo.marvel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mn.turbo.marvel.data.local.entiry.MovieEntity

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: MovieEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movieEntity: List<MovieEntity>)

    @Query("select * from movies")
    suspend fun selectAll(): List<MovieEntity>
}