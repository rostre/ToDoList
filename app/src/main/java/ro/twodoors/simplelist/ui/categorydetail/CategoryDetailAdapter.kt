package ro.twodoors.simplelist.ui.categorydetail

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.ui.main.TodoClickListener

class CategoryDetailAdapter(val onClickListener : TodoClickListener)
    : ListAdapter<Todo, CategoryDetailViewHolder>(
    TodoDiffCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryDetailViewHolder {
        return CategoryDetailViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CategoryDetailViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo, onClickListener)
    }

    fun getTodoAtPosition(position: Int) : Todo {
        return this.currentList[position]
    }
}

class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
    override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
        return oldItem == newItem
    }
}