package com.commonsware.todo

object ToDoRepository {
    val items = listOf(
        ToDoModel(
            description = "3 hours of leet code",
            isCompleted = true,
            notes = "See https://leetcode.com/problemset/all/"
        ),
        ToDoModel(
            description = "Work on a project"
        ),
        ToDoModel(
            description = "Job search",
            notes = "https://www.linkedin.com/jobs/"
        )
    )
}