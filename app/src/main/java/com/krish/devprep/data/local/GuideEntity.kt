package com.krish.devprep.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "guide")
data class GuideEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val step: Int,
    val title: String,
    val level: String,
    val whatToLearn: List<String>,
    val explanation: String,
    val resources: List<String>
)
