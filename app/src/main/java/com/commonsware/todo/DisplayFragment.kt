
package com.commonsware.todo

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.databinding.TodoDisplayBinding

class DisplayFragment : Fragment() {
    private val args: DisplayFragmentArgs by navArgs()
    private lateinit var binding: TodoDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = TodoDisplayBinding.inflate(inflater, container, false)
        .apply { binding = this }
        .root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.model = ToDoRepository.find(args.modelId)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.actions_display, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.edit -> { edit(); return true; }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun edit() {
        findNavController().navigate(DisplayFragmentDirections.editModel(args.modelId))
    }
}

