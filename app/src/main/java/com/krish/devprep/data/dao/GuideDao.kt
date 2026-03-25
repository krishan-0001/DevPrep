package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krish.devprep.data.local.GuideEntity

@Dao
interface GuideDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE )
    suspend fun insertAll(guides: List<GuideEntity>)

    @Query("SELECT * FROM guide ORDER BY `order` ASC")
    suspend fun getAllGuides(): List<GuideEntity>

}