package com.commonsware.todo
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView (R.layout.activity_main)

    toolbar.title = getString(R.string.app_name)
    toolbar . inflateMenu (R.menu.actions)

    toolbar.setOnMenuItemClickListener { item ->
      when (item.itemId) {
        R.id.about -> startActivity(Intent(this, AboutActivity::class.java))
        else -> return@setOnMenuItemClickListener false
      }
      true
    }
  }
}