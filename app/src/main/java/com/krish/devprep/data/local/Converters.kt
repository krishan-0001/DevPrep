package com.krish.devprep.data.local

import androidx.room.TypeConverters

class Converters {

    @TypeConverters
    fun fromList(list: List<String>): String {
        return list.joinToString(",")
    }
    @TypeConverters
    fun toList(data: String): List<String> {
        return data.split(",")
    }


}