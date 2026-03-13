package com.example.devprep.data.local

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
}