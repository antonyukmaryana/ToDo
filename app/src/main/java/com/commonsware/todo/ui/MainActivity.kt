package com.commonsware.todo.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.commonsware.todo.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
  private lateinit var appBarConfiguration: AppBarConfiguration

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

    setSupportActionBar(toolbar)

    findNavController(R.id.nav_host).let { nav ->
      appBarConfiguration = AppBarConfiguration(nav.graph)
      setupActionBarWithNavController(nav, appBarConfiguration)
    }
  }
  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.actions, menu)

    return super.onCreateOptionsMenu(menu)
  }
  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == R.id.about) {
    startActivity(Intent(this, AboutActivity::class.java))
    return true
  }

    return super.onOptionsItemSelected(item)
  }
  override fun onSupportNavigateUp() =
    navigateUp(findNavController(R.id.nav_host), appBarConfiguration)
}