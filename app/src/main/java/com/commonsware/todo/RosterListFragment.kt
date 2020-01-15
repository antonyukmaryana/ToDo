package com.commonsware.todo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.todo_roster.*
import kotlinx.android.synthetic.main.todo_roster.view.*


class RosterListFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.todo_roster, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val adapter = RosterAdapter(
            inflater = layoutInflater, onCheckboxToggle = { model ->
                ToDoRepository.save(model.copy(isCompleted = !model.isCompleted)) },
            onRowClick = { })

        view.items.apply {
            setAdapter(adapter)
            layoutManager = LinearLayoutManager(context)
            addItemDecoration(
                DividerItemDecoration(
                    activity,
                    DividerItemDecoration.VERTICAL
                )
            )
        }
        adapter.submitList(ToDoRepository.items)
        empty.visibility = View.GONE
    }
    private fun display(model: ToDoModel) {
        findNavController(view!!).navigate(RosterListFragmentDirections.displayModel(model.id))
    }
}
