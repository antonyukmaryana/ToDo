package com.commonsware.todo.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

enum class FilterMode { ALL, OUTSTANDING, COMPLETED }

class ToDoRepository(private val store: ToDoEntity.Store) {

    fun items(filterMode: FilterMode = FilterMode.ALL): LiveData<List<ToDoModel>> =
        Transformations.map(filteredEntities(filterMode)) { all -> all.map { it.toModel() } }

    fun find(id: String): LiveData<ToDoModel> =
        Transformations.map(store.find(id)) { it.toModel() }

    suspend fun save(model: ToDoModel) {
        store.save(ToDoEntity(model))
    }
    suspend fun delete(model: ToDoModel) {
        store.delete(ToDoEntity(model))
    }
    private fun filteredEntities(filterMode: FilterMode) = when (filterMode) {
        FilterMode.ALL -> store.all()
        FilterMode.OUTSTANDING -> store.filtered(isCompleted = false)
        FilterMode.COMPLETED -> store.filtered(isCompleted = true)
    }
}