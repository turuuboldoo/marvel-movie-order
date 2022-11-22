package mn.turbo.marvel.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import mn.turbo.marvel.data.local.entiry.TvShowEntity

@Dao
interface TvShowDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShowEntity: TvShowEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(tvShowEntity: List<TvShowEntity>)

    @Query("select * from tv_shows")
    suspend fun selectAll(): List<TvShowEntity>

    @Query("select * from tv_shows where id = :tvShowId")
    suspend fun selectTvShowById(tvShowId: Int): TvShowEntity
}
