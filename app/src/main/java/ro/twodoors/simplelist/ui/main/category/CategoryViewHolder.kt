package ro.twodoors.simplelist.ui.main.category

import android.graphics.Color
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.RecyclerView
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.data.Category
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.databinding.CategoryListItemBinding
import ro.twodoors.simplelist.ui.main.CategoryClickListener
import ro.twodoors.simplelist.utils.SharedPrefsHelper
import ro.twodoors.simplelist.utils.createGradientDrawable
import ro.twodoors.simplelist.utils.getStringResource

class CategoryViewHolder(val binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root){
    private var color = 0

    fun bind(item: Category, clickListener: CategoryClickListener){
        binding.apply {
            category = item
            setupColors(item.name)
            this.clickListener = clickListener
            executePendingBindings()
        }
    }

    private fun setupColors(name: String){
        color = SharedPrefsHelper.getColorFromPref(itemView.context, name)
        setupProgressBarColor()
        setupTaskCompletedColor()
        setupCategoryColor()
    }

    private fun setupProgressBarColor(){
        val drawable = binding.progressBar.progressDrawable as LayerDrawable
        drawable.mutate()
        drawable.findDrawableByLayerId(R.id.fill_shape).setTint(color)
    }

    private fun setupTaskCompletedColor(){
        binding.tvTasksCompleted.background =
            createGradientDrawable(24F, ColorUtils.blendARGB(color, Color.WHITE, 0.95F))
    }

    private fun setupCategoryColor(){
        binding.tvCategoryColor.background =
            createGradientDrawable(32F, color)
    }

    fun setupTodos(todos: List<Todo>){
        val allTasks = getAllTasksCount(todos)
        val completedTasks = getCompletedTasksCount(todos)
        val leftTasks = allTasks - completedTasks


        binding.tvCategoryTasks.text = itemView.getStringResource(R.string.count_tasks, allTasks)

        val progressValue = calculateProgress(allTasks, completedTasks)
        binding.progressBar.progress = progressValue
        binding.tvProgress.apply {
            text = itemView.getStringResource(R.string.progress_percentage, progressValue)
            setTextColor(color)
        }

        binding.tvTasksCompleted.apply {
            text = itemView.getStringResource(R.string.tasks_completed, completedTasks)
            setTextColor(color)
        }
        if (leftTasks > 0 ){
            binding.tvTasksLeft.apply {
                visibility = View.VISIBLE
                text = itemView.getStringResource(R.string.tasks_left, leftTasks)
            }
        } else
            binding.tvTasksLeft.visibility = View.GONE
    }

    private fun getAllTasksCount(tasks: List<Todo>) : Int {
        return tasks.filter { it.category == binding.category?.name }.count()
    }

    private fun getCompletedTasksCount(tasks: List<Todo>) : Int {
        return tasks.filter { it.category ==  binding.category?.name && it.completed}.count()
    }

    private fun calculateProgress(allTasks : Int, completedTasks: Int) : Int {
        return if (allTasks == 0) 0
        else completedTasks * 100 / allTasks
    }

    companion object {
        fun from(parent: ViewGroup) : RecyclerView.ViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CategoryListItemBinding.inflate(layoutInflater, parent, false)
            return CategoryViewHolder(
                binding
            )
        }
    }
}