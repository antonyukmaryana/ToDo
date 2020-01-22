package com.commonsware.todo.ui.roster

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.commonsware.todo.R
import com.commonsware.todo.repo.FilterMode
import com.commonsware.todo.repo.ToDoModel
import kotlinx.android.synthetic.main.todo_roster.*
import kotlinx.android.synthetic.main.todo_roster.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class RosterListFragment : Fragment() {
    private val motor: RosterMotor by viewModel()
    private val menuMap = mutableMapOf<FilterMode, MenuItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.todo_roster, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter =
            RosterAdapter(
                inflater = layoutInflater,
                onCheckboxToggle = { model ->
                    motor.save(model.copy(isCompleted = !model.isCompleted))
                },
                onRowClick = { model -> display(model) })

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

        motor.states.observe(this, Observer { state ->
            adapter.submitList(state.items)

            when {
                state.items.isEmpty() && state.filterMode == FilterMode.ALL -> {
                    empty.visibility = View.VISIBLE
                    empty.setText(R.string.msg_empty)
                }
                state.items.isEmpty() -> {
                    empty.visibility = View.VISIBLE
                    empty.setText(R.string.msg_empty_filtered)
                }
                else -> empty.visibility = View.GONE
            }

            loading.visibility = View.GONE
            menuMap[state.filterMode]?.isChecked = true
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_roster, menu)

        menuMap.apply {
            put(FilterMode.ALL, menu.findItem(R.id.all))
            put(FilterMode.COMPLETED, menu.findItem(R.id.completed))
            put(FilterMode.OUTSTANDING, menu.findItem(R.id.outstanding))
        }

        motor.states.value?.let { menuMap[it.filterMode]?.isChecked = true }

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                add()
                return true
            }
            R.id.all -> {
                item.isChecked = true
                motor.load(FilterMode.ALL)
                return true
            }
            R.id.completed -> {
                item.isChecked = true
                motor.load(FilterMode.COMPLETED)
                return true
            }
            R.id.outstanding -> {
                item.isChecked = true
                motor.load(FilterMode.OUTSTANDING)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun display(model: ToDoModel) {
        findNavController().navigate(
            RosterListFragmentDirections.displayModel(
                model.id
            )
        )
    }

    private fun add() {
        findNavController().navigate(RosterListFragmentDirections.createModel())
    }
}
