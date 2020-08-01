package ro.twodoors.todolist.view.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.shapes.Shape
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ro.twodoors.todolist.databinding.ListItemBinding
import ro.twodoors.todolist.model.Todo

class MainAdapter(val onClickListener : OnClickListener) : ListAdapter<Todo, MainAdapter.TodoViewHolder>(TodoDiffCallback()) {

    fun getTodoAtPosition(position: Int) : Todo{
        return this.currentList.get(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder.from(
            parent
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo, onClickListener)
    }

    class TodoViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Todo, clickListener: OnClickListener){
            binding.todo = item
            val sharedPref = itemView.context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE)
            val color = sharedPref.getInt(item.categoryName, 0)
            val gradientDrawable = GradientDrawable()

            gradientDrawable.cornerRadius = 32F
            gradientDrawable.setColor(color)
            binding.txtTaskColor.background = gradientDrawable
//            binding.taskContainer.setBackgroundColor(ColorUtils.blendARGB(color, Color.WHITE, 0.95F))

            val gradientDrawableOutline = GradientDrawable()
            gradientDrawableOutline.cornerRadius = 24F
            gradientDrawableOutline.setStroke(4, sharedPref.getInt(item.categoryName, 0))
            gradientDrawableOutline.setColor(ColorUtils.blendARGB(color, Color.WHITE, 0.95F))
            binding.taskContainer.background = gradientDrawableOutline

            //binding.txtTaskColor.setBackgroundColor(sharedPref.getInt(item.categoryName, 0))
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : TodoViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ListItemBinding.inflate(layoutInflater, parent, false)
                return TodoViewHolder(
                    binding
                )
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