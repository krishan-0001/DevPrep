package com.krish.devprep.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guide")
data class GuideEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val title: String,
    val description: String,
    val level: String,
    val order: Int,
    val content: String,
    val resources: List<String>

    )