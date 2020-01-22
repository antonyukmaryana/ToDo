package com.commonsware.todo.ui

import androidx.lifecycle.*
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class SingleModelViewState(
    val item: ToDoModel? = null
)

class SingleModelMotor(
    private val repo: ToDoRepository,
    modelId: String?,
    private val uiContext: CoroutineContext = Dispatchers.Main
) : ViewModel() {
    val states: LiveData<SingleModelViewState> =
        modelId?.let { Transformations.map(repo.find(modelId)) { SingleModelViewState(it) } }
            ?: MutableLiveData<SingleModelViewState>().apply { value = SingleModelViewState(null) }

    fun save(model: ToDoModel) {
        viewModelScope.launch(uiContext) {
            repo.save(model)
        }
    }

    fun delete(model: ToDoModel) {
        viewModelScope.launch(uiContext) {
            repo.delete(model)
        }
    }
}
