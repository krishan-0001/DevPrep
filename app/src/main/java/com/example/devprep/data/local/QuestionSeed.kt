package com.example.devprep.data.local

import android.R.attr.category

object QuestionSeed {

    val questions = listOf(

        QuestionEntity(
            category = "Kotlin",
            question = "What is Kotlin?",
            option1 = "Database",
            option2 = "Programming language",
            option3 = "Operating System",
            option4 = "Framework",
            correctAnswer = 2,
            explanation = "Kotlin is a modern statically typed programming language developed by JetBrains for Android and backend development."
        ),

        QuestionEntity(
            category = "Kotlin",
            question = "Difference between val and var?",
            option1 = "Both mutable",
            option2 = "val mutable var immutable",
            option3 = "val immutable var mutable",
            option4 = "Both immutable",
            correctAnswer = 3,
            explanation = "val is read-only while var allows reassignment."
        ),

        QuestionEntity(
            category = "Jetpack Compose",
            question = "What is Jetpack Compose?",
            option1 = "UI toolkit",
            option2 = "Database",
            option3 = "Networking library",
            option4 = "IDE",
            correctAnswer = 1,
            explanation = "Jetpack Compose is Android’s modern toolkit for building native UI using Kotlin."
        )

    )
}