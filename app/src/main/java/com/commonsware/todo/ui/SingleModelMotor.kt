package com.commonsware.todo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository

class SingleModelViewState(
    val item: ToDoModel? = null
)
class SingleModelMotor(
    repo: ToDoRepository, modelId: String) : ViewModel() {
    val states: LiveData<SingleModelViewState> =
    Transformations.map(repo.find(modelId)) { SingleModelViewState(it) }

    fun save(model: ToDoModel) = repo.save(model)

    fun delete(model: ToDoModel) = repo.delete(model)
}