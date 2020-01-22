package com.commonsware.todo.ui.edit

import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.R
import com.commonsware.todo.databinding.TodoEditBinding
import com.commonsware.todo.repo.ToDoModel
import com.commonsware.todo.ui.SingleModelMotor
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditFragment : Fragment() {
    private lateinit var binding: TodoEditBinding
    private val args: EditFragmentArgs by navArgs()
    private val motor: SingleModelMotor by viewModel { parametersOf(args.modelId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = TodoEditBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        motor.states.observe(this, Observer { state -> binding.model = state.item } )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_edit, menu)
        menu.findItem(R.id.delete).isVisible = args.modelId != null

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> { save(); return true; }
            R.id.delete -> { delete(); return true; }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        val edited = if (binding.model == null) {
            ToDoModel(
                description = binding.desc.text.toString(),
                isCompleted = binding.isCompleted.isChecked,
                notes = binding.notes.text.toString()
            )
        } else {
            binding.model?.copy(
                description = binding.desc.text.toString(),
                isCompleted = binding.isCompleted.isChecked,
                notes = binding.notes.text.toString()
            )
        }

        edited?.let { motor.save(it) }
        navToDisplay()
    }

    private fun delete() {
        binding.model?.let { motor.delete(it) }
        navToList()
    }

    private fun navToDisplay() {
        hideKeyboard()
        findNavController().popBackStack()
    }

    private fun navToList() {
        hideKeyboard()
        findNavController().popBackStack(R.id.rosterListFragment, false)
    }

    private fun hideKeyboard() {
        view?.let {
            val imm = context?.getSystemService<InputMethodManager>()

            imm?.hideSoftInputFromWindow(
                it.windowToken,
                InputMethodManager.HIDE_NOT_ALWAYS
            )
        }
    }
}
