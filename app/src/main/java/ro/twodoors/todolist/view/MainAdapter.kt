package ro.twodoors.todolist.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item.view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ro.twodoors.todolist.databinding.ListItemBinding
import ro.twodoors.todolist.model.Todo

class MainAdapter(val onClickListener : OnClickListener) : ListAdapter<Todo, MainAdapter.TodoViewHolder>(TodoDiffCallback()) {

    fun getTodoAtPosition(position: Int) : Todo{
        return this.currentList.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo, onClickListener)
    }

    class TodoViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Todo, clickListener: OnClickListener){
            binding.todo = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : TodoViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return TodoViewHolder(binding)
            }
        }
    }

    interface OnClickListener {
        fun onCheckboxChecked(id: Int, isChecked: Boolean)
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