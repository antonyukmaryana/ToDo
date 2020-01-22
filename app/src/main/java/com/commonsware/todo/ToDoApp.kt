package com.commonsware.todo

import android.app.Application
import com.commonsware.todo.repo.ToDoDatabase
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.ui.SingleModelMotor
import com.commonsware.todo.ui.roster.RosterMotor
import org.koin.android.ext.android.startKoin
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class ToDoApp : Application() {
    private val koinModule = module {
        single { ToDoDatabase.newInstance(androidContext()) }
        single {
            val db: ToDoDatabase = get()

            ToDoRepository(db.todoStore())
        }
        viewModel { RosterMotor(get()) }
        viewModel { (modelId: String) -> SingleModelMotor(get(), modelId) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(koinModule))
    }
}
