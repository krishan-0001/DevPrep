package com.krish.devprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.krish.devprep.data.Theory
import com.krish.devprep.data.local.TheoryEntity

@Dao
interface TheoryDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTheory(theory: List<TheoryEntity>)
    @Query("SELECT * FROM theory WHERE category = :category")
    suspend fun getTheoryByCategory(category: String): List<Theory>

    @Query("SELECT * FROM theory WHERE id = :id")
    suspend fun getTheoryById(id: Int): Theory
}