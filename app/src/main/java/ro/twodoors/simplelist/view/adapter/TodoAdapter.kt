package ro.twodoors.simplelist.view.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ro.twodoors.simplelist.databinding.ListItemBinding
import ro.twodoors.simplelist.model.Todo
import ro.twodoors.simplelist.utils.SharedPrefsHelper
import ro.twodoors.simplelist.view.TodoClickListener

class TodoAdapter(val onClickListener : TodoClickListener) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    fun getTodoAtPosition(position: Int) : Todo {
        return this.currentList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo, onClickListener)
    }

    class TodoViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Todo, clickListener: TodoClickListener){
            binding.todo = item
            setupColors(item.category)
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        private fun setupColors(categoryName: String){
            val color = SharedPrefsHelper.getColorFromPref(itemView.context, categoryName)
            setupTaskColor(color)
            setupTaskContainerColor(color)
        }

        private fun setupTaskColor(color : Int){
            val drawable = GradientDrawable().apply {
                cornerRadius = 32F
                setColor(color)
            }
            binding.txtTaskColor.background = drawable
        }

        private fun setupTaskContainerColor(color: Int){
            val drawable = GradientDrawable().apply {
                cornerRadius = 24F
                setStroke(4, color)
                setColor(ColorUtils.blendARGB(color, Color.WHITE, 0.95F))
            }
            binding.taskContainer.background = drawable
        }

        companion object {
            fun from(parent: ViewGroup) : TodoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return TodoViewHolder(binding)
            }
        }
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