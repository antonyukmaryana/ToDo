package com.commonsware.todo.ui.roster

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository

class RosterViewState(
    val items: List<ToDoModel> = listOf()
)
class RosterMotor(private val repo: ToDoRepository) : ViewModel() {
    val states: LiveData<RosterViewState> = Transformations.map(repo.items) { RosterViewState(it) }

    fun save(model: ToDoModel) = repo.save(model)
}