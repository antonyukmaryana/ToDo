package com.commonsware.todo

import androidx.recyclerview.widget.RecyclerView
import com.commonsware.todo.databinding.TodoRowBinding

class RosterRowHolder(private val binding: TodoRowBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(model: ToDoModel) {
        binding.model = model
        binding.executePendingBindings()
    }
}