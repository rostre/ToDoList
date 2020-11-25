package ro.twodoors.simplelist.ui.main.todo

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.databinding.ListItemBinding
import ro.twodoors.simplelist.ui.main.TodoClickListener
import ro.twodoors.simplelist.utils.SharedPrefsHelper

class TodoViewHolder(val binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(item: Todo, clickListener: TodoClickListener){
        binding.todo = item
        setupColors(item.category)
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    private fun setupColors(categoryName: String){
        val color = SharedPrefsHelper.getColorFromPref(itemView.context, categoryName)
        binding.ckbCompleted.buttonTintList = ColorStateList.valueOf(color)
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