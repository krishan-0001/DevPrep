package com.krish.devprep.data.local

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object JsonLoader {
    fun loadQuestions(context: Context): List<QuestionEntity>{
        val jsonString =
            context.assets.open("questions.json")
                .bufferedReader()
                .use{it.readText()}
        val type = object : TypeToken<List<QuestionEntity>>() {}.type
        return Gson().fromJson(jsonString,type)
    }

    fun loadGuides(context: Context): List<GuideEntity>{
        val jsonString = context.assets.open("guide_data.json")
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<List<GuideEntity>>() {}.type
        return Gson().fromJson(jsonString,type)
    }

    fun loadCodingQuestions(context: Context): List<CodingQuestionEntity> {
        val jsonString = context.assets.open("coding_questions.json")
            .bufferedReader()
            .use { it.readText() }

        val type = object : TypeToken<List<CodingQuestionEntity>>() {}.type
        return Gson().fromJson(jsonString, type)
    }
    fun loadTheory(context: Context): List<TheoryEntity> {
        val jsonString = context.assets.open("theory.json")
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<List<TheoryEntity>>() {}.type
        return Gson().fromJson(jsonString, type)
    }

    fun loadHr(context: Context): List<HrEntity>{
        val jsonString = context.assets.open("hr_questions.json")
            .bufferedReader()
            .use { it.readText() }
        val type = object : TypeToken<List<HrEntity>>() {}.type
        return Gson().fromJson(jsonString, type)

    }
}