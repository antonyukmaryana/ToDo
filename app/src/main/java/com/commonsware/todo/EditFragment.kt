package com.commonsware.todo

import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.content.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.databinding.TodoEditBinding

class EditFragment : Fragment() {
    private lateinit var binding: TodoEditBinding
    private val args: EditFragmentArgs by navArgs()

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
        binding.model = ToDoRepository.find(args.modelId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_edit, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.save -> { save(); return true; }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun save() {
        val edited = binding.model?.copy(
            description = binding.desc.text.toString(),
            isCompleted = binding.isCompleted.isChecked,
            notes = binding.notes.text.toString()
        )

        edited?.let { ToDoRepository.save(it) }
        navToDisplay()
    }

    private fun navToDisplay() {
        hideKeyboard()
        findNavController().popBackStack()
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
