package com.commonsware.todo.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

class ToDoRepository(private val store: ToDoEntity.Store) {
    val items: LiveData<List<ToDoModel>> =
        Transformations.map(store.all()) { all -> all.map { it.toModel() } }

    fun find(id: String): LiveData<ToDoModel> =
        Transformations.map(store.find(id)) { it.toModel() }

    suspend fun save(model: ToDoModel) {
        store.save(ToDoEntity(model))
    }

    suspend fun delete(model: ToDoModel) {
        store.delete(ToDoEntity(model))
    }
}
