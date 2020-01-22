package com.commonsware.todo.repo

import androidx.room.TypeConverter
import java.util.*

class TypeTransmogrifier {
    @TypeConverter
    fun fromCalendar(date: Calendar?): Long? = date?.timeInMillis

    @TypeConverter
    fun toCalendar(millisSinceEpoch: Long?): Calendar? = millisSinceEpoch?.let {
        Calendar.getInstance().apply { timeInMillis = millisSinceEpoch }
    } }