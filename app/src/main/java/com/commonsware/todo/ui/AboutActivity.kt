package com.commonsware.todo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.commonsware.todo.R
import kotlinx.android.synthetic.main.activity_about.*

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        toolbar.title = getString(R.string.app_name)
        about.loadUrl("file:///android_asset/about.html")
    }
}
