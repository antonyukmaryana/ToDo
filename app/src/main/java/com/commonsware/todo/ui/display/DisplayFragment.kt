package com.commonsware.todo.ui.display

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.commonsware.todo.R
import com.commonsware.todo.databinding.TodoDisplayBinding
import com.commonsware.todo.ui.SingleModelMotor
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DisplayFragment : Fragment() {
    private val args: DisplayFragmentArgs by navArgs()
    private lateinit var binding: TodoDisplayBinding
    private val motor: SingleModelMotor by viewModel { parametersOf(args.modelId) }

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
        motor.states.observe(this, Observer { state -> binding.model = state.item } )
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
        findNavController().navigate(
            DisplayFragmentDirections.editModel(
                args.modelId
            )
        )
    }
}
