package com.example.devprep.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val question: String,
    val isBookmarked: Boolean = false,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctAnswer: Int,
    val explanation: String

)

@Entity(tableName = "quiz_stats")
data class QuizStatsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val quizzesAttempted: Int =1,
    val totalScore: Int =0,
    val totalQuestions: Int =0
)

@Entity(tableName = "category_stats")
data class CategoryStatsEntity(

    @PrimaryKey
    val category: String,

    val totalQuestions: Int,

    val correctAnswers: Int
)