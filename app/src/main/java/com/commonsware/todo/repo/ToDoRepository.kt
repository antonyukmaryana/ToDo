package com.commonsware.todo.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations

class ToDoRepository {
    private val _items =
    MutableLiveData<List<ToDoModel>>().apply { value = listOf() }
    val items: LiveData<List<ToDoModel>> = _items

    suspend fun save(model: ToDoModel) {
        _items.value = if (current().any { it.id == model.id }) {
            current().map { if (it.id == model.id) model else it }
        } else {
            current() + model
        }
    }
    suspend fun delete(model: ToDoModel) {
        _items.value = current().filter { it.id != model.id }
    }

    fun find(modelId: String?): LiveData<ToDoModel> =
        Transformations.map(items) {
        it.find { model -> model.id == modelId }
        }
    private fun current() = _items.value!!
}