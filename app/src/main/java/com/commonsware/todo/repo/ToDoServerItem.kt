package com.commonsware.todo.repo

import android.annotation.SuppressLint
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.squareup.moshi.ToJson
import java.text.SimpleDateFormat
import java.util.*

@JsonClass(generateAdapter = true)
data class ToDoServerItem(
    val description: String,
    val id: String,
    val completed: Boolean,
    val notes: String,
    @Json(name = "created_on") val createdOn: Calendar
) {
    fun toEntity(): ToDoEntity {
        return ToDoEntity(
            id = id,
            description = description,
            isCompleted = completed,
            notes = notes,
            createdOn = createdOn
        )
    }
}

@SuppressLint("SimpleDateFormat")
private val FORMATTER = SimpleDateFormat("yyyy-MM-dd")

class MoshiCalendarAdapter {
    @ToJson
    fun toJson(date: Calendar) = FORMATTER.format(date.time)

    @FromJson
    fun fromJson(dateString: String): Calendar =
        Calendar.getInstance().apply { time = FORMATTER.parse(dateString) }
}
