package com.commonsware.todo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import android.content.Intent
import kotlinx.android.synthetic.main.todo_roster.*


class RosterListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.todo_roster, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar.title = getString(R.string.app_name)
        toolbar.inflateMenu(R.menu.actions)

        toolbar.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.about -> startActivity(Intent(activity, AboutActivity::class.java))
                else -> return@setOnMenuItemClickListener false
            }
            true
        }
    }
}
