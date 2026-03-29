package com.krish.devprep.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "hr_questions")
data class HrEntity(
    @PrimaryKey
    val id: Int,
    val question: String,
    val answer: String,
    val category: String,
    val tips: String,
    val mistakes: String,
    val isBookmarked: Boolean = false
)