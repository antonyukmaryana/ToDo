package com.commonsware.todo.ui.roster

import android.content.Context
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.lifecycle.*
import com.commonsware.todo.BuildConfig
import com.commonsware.todo.repo.FilterMode
import com.commonsware.todo.repo.PrefsRepository
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.report.RosterReport
import com.commonsware.todo.ui.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File

class RosterViewState(
    val items: List<ToDoModel> = listOf(),
    val filterMode: FilterMode = FilterMode.ALL
)

sealed class Nav {
    data class ViewReport(val doc: Uri) : Nav()
    data class ShareReport(val doc: Uri) : Nav()
}

private const val AUTHORITY = BuildConfig.APPLICATION_ID + ".provider"

class RosterMotor(
    private val repo: ToDoRepository,
    private val prefs: PrefsRepository,
    private val report: RosterReport,
    private val context: Context
) : ViewModel() {
    private val _states = MediatorLiveData<RosterViewState>()
    val states: LiveData<RosterViewState> = _states
    private var lastSource: LiveData<List<ToDoModel>>? = null
    private val _navEvents = MutableLiveData<Event<Nav>>()
    val navEvents: LiveData<Event<Nav>> = _navEvents

    init {
        load(FilterMode.ALL)
    }

    fun load(filterMode: FilterMode) {
        lastSource?.let { _states.removeSource(it) }

        val items = repo.items(filterMode)

        _states.addSource(items) { models ->
            _states.value = RosterViewState(models, filterMode)
        }

        lastSource = items
    }

    fun save(model: ToDoModel) {
        viewModelScope.launch(Dispatchers.Main) {
            repo.save(model)
        }
    }

    fun saveReport(doc: Uri) {
        viewModelScope.launch(Dispatchers.Main) {
            _states.value?.let { report.generate(it.items, doc) }
            _navEvents.postValue(Event(Nav.ViewReport(doc)))
        }
    }

    fun shareReport() {
        viewModelScope.launch(Dispatchers.Main) {
            saveForSharing()
        }
    }

    fun importItems() {
        viewModelScope.launch(Dispatchers.Main) {
            repo.importItems(prefs.loadWebServiceUrl())
        }
    }

    private suspend fun saveForSharing() {
        withContext(Dispatchers.IO) {
            val shared = File(context.cacheDir, "shared").also { it.mkdirs() }
            val reportFile = File(shared, "report.html")
            val doc = FileProvider.getUriForFile(context, AUTHORITY, reportFile)

            _states.value?.let { report.generate(it.items, doc) }
            _navEvents.postValue(Event(Nav.ShareReport(doc)))
        }
    }
}
