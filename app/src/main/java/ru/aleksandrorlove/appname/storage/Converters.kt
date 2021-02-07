package ru.aleksandrorlove.appname.storage

import androidx.room.TypeConverter

class Converters {
    @TypeConverter
    fun toListOfStrings(flatStringList: String): List<String> {
        return flatStringList.split(",")
    }

    @TypeConverter
    fun fromListStringOfStrings(listOfString: List<String>): String {
        return listOfString.joinToString(",")
    }

    @TypeConverter
    fun toListOfInt(flatStringList: String): List<Int> {
        val listInt: MutableList<Int> = mutableListOf()
        val listString: List<String> = flatStringList.split(",").map { it.trim() }
        listString.forEach {
            listInt.add(it.toInt())
        }
        return listInt.toList()
    }

    @TypeConverter
    fun fromListIntOfStrings(listOfInt: List<Int>): String {
        return listOfInt.joinToString(",")
    }
}