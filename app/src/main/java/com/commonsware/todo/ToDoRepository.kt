package com.commonsware.todo

object ToDoRepository {
    var items = listOf(
        ToDoModel(
            description = "3 hours of leetcode",
            isCompleted = true,
            notes = "See https://leetcode.com/problemset/all/"
        ),
        ToDoModel(
            description = "Work on a project"
        ),
        ToDoModel(
            description = "Job search",
            notes = "See https://www.linkedin.com/jobs/"
        )
    )
    fun save (model: ToDoModel){
        items = if (items.any {it.id == model.id }) {
            items.map { if (it.id == model.id) model else it }
        } else {
            items + model

        }
    }
}
