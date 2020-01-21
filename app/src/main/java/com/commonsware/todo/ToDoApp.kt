package com.commonsware.todo

import android.app.Application
import org.koin.dsl.module.module
import org.koin.android.ext.android.startKoin

class ToDoApp() : Application () {
    private val koinModule = module {
        single { ToDoRepository() }
    }

    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(koinModule))
    }
}