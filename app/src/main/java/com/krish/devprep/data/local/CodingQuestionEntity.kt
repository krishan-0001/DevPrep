package com.krish.devprep.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "coding_questions")
data class CodingQuestionEntity(
    @PrimaryKey
    val id: Int,
    val category: String,
    val question: String,
    val code: String,
    val explanation: String,
    val difficulty: String,
    val company: String,
    val isBookmarked: Boolean = false
)
