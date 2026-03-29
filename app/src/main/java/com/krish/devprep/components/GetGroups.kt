package com.krish.devprep.components

import com.krish.devprep.data.CategoryGroup

fun getGroups(module: String): List<CategoryGroup>{
    return when(module){
        "MCQ", "Theory" -> listOf(
            CategoryGroup("Core Android",listOf("Android Basics", "Jetpack Compose", "Navigation")),
            CategoryGroup("Architecture", listOf("MVVM", "Hilt", "System Design")),
            CategoryGroup("Data & APIs", listOf("Retrofit_API", "Room Database", "Firebase")),
            CategoryGroup("Async", listOf("Coroutines", "Flow & StateFlow")),
            CategoryGroup("Language", listOf("Kotlin"))
        )
        "Coding" -> listOf(
            CategoryGroup("DSA", listOf("Arrays", "Strings")),
            CategoryGroup("Android Coding", listOf("UI", "State Management"))
        )
        "HR" -> listOf(
            CategoryGroup("Basics", listOf("Introduction", "Strengths & Weakness")),
            CategoryGroup("Behavioral", listOf("Challenges", "Teamwork")),
            CategoryGroup("Company", listOf("Company Questions")),
            CategoryGroup("Situational", listOf("Problem Solving")),
            CategoryGroup("Resume", listOf("Projects")),
            CategoryGroup("Personality", listOf("Communication")),
            CategoryGroup("Technical Round", listOf("Core Concepts","Project Questions", "Android Questions", "Coding Logic", "Debugging"))
        )
        else -> emptyList()

    }

}