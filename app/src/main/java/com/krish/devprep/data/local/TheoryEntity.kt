package com.krish.devprep.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theory")
data class TheoryEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val content: String,
    val category: String
)