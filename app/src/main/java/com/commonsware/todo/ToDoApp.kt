package com.commonsware.todo

import android.app.Application
import com.commonsware.todo.repo.ToDoRepository
import com.commonsware.todo.ui.roster.RosterMotor
import org.koin.android.ext.android.startKoin
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

class ToDoApp : Application() {
    private val koinModule = module {
        single { ToDoRepository() }
        viewModel { RosterMotor(get()) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(koinModule))
    }
}
