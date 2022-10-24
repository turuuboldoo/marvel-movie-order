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

    @Query("select * from movies where id = :movieId")
    suspend fun selectById(movieId: Int): MovieEntity

    @Query("SELECT * FROM movies ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getPagedList(limit: Int, offset: Int): List<MovieEntity>
}