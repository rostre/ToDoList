package ro.twodoors.simplelist.ui.categorydetail

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.databinding.CategoryDetailTodoItemBinding
import ro.twodoors.simplelist.ui.main.TodoClickListener
import ro.twodoors.simplelist.utils.SharedPrefsHelper

class CategoryDetailViewHolder(val binding: CategoryDetailTodoItemBinding)
    : RecyclerView.ViewHolder(binding.root){

    fun bind(item: Todo, clickListener: TodoClickListener){
        binding.todo = item
        setupColors(item.category)
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

    private fun setupColors(categoryName: String){
        val color = SharedPrefsHelper.getColorFromPref(itemView.context, categoryName)
        binding.ckbCompleted.buttonTintList = ColorStateList.valueOf(color)
    }

    companion object {
        fun from(parent: ViewGroup) : CategoryDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CategoryDetailTodoItemBinding.inflate(layoutInflater, parent, false)
            return CategoryDetailViewHolder(binding)
        }
    }
}