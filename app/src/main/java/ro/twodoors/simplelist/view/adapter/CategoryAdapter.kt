package ro.twodoors.simplelist.view.adapter

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.ColorUtils
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.databinding.CategoryAddItemBinding
import ro.twodoors.simplelist.databinding.CategoryListItemBinding
import ro.twodoors.simplelist.model.Category
import ro.twodoors.simplelist.model.Todo
import ro.twodoors.simplelist.utils.SharedPrefsHelper
import ro.twodoors.simplelist.view.CategoryClickListener
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_ITEM = 0
private const val ITEM_VIEW_TYPE_ADD_CATEGORY = 1

class CategoryAdapter(val onClickListener: CategoryClickListener)
    : ListAdapter<DataItem, RecyclerView.ViewHolder>(CategoryDiffCallback()) {

    private var tasks: List<Todo> = listOf()
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun populateCategories(list : List<Category>?){
        adapterScope.launch {
            val items = if(list.isNullOrEmpty())
                            listOf(DataItem.AddCategory)
                        else
                            list.map { DataItem.CategoryItem(it) } + listOf(DataItem.AddCategory)

            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    fun setTodos(tasks: List<Todo>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_ITEM -> CategoryViewHolder.from(parent)

            ITEM_VIEW_TYPE_ADD_CATEGORY -> AddCategoryViewHolder.from(parent)

            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.CategoryItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.AddCategory -> ITEM_VIEW_TYPE_ADD_CATEGORY
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CategoryViewHolder -> {
                val item = getItem(position) as DataItem.CategoryItem
                holder.apply {
                    bind(item.category, onClickListener)
                    setupTasks(tasks)
                }
            }

            is AddCategoryViewHolder -> {
                holder.bind(onClickListener)
            }
        }
    }

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
            setupTaskCompletedColor(color)
            setupCategoryColor(color)
        }

        private fun setupProgressBarColor(){
            val drawable = binding.progressBar.progressDrawable as LayerDrawable
            drawable.mutate()
            drawable.findDrawableByLayerId(R.id.fill_shape).setTint(color)
        }

        private fun setupTaskCompletedColor(color : Int){
            val drawable = GradientDrawable().apply {
                cornerRadius = 24F
                setColor(ColorUtils.blendARGB(color, Color.WHITE, 0.95F))
            }
            binding.tvTasksCompleted.background = drawable
        }

        private fun setupCategoryColor(color: Int){
            val drawable = GradientDrawable().apply {
                cornerRadius = 32F
                setColor(color)
            }
            binding.tvCategoryColor.background = drawable
        }

        fun setupTasks(tasks: List<Todo>){
            val allTasks =
                tasks.filter { it.category == binding.category?.name }.count()
            val completedTasks =
                tasks.filter { it.category == binding.category?.name && it.completed }.count()
            val leftTasks = allTasks - completedTasks

            binding.tvCategoryTasks.text = "${allTasks} tasks"

            val progressValue = if (allTasks == 0) 0 else completedTasks * 100 / allTasks
            binding.progressBar.progress = progressValue
            binding.tvProgress.apply {
                text = "$progressValue%"
                setTextColor(color)
            }

            binding.tvTasksCompleted.apply {
                text = "$completedTasks completed"
                setTextColor(color)
            }
            if (leftTasks > 0 ){
                binding.tvTasksLeft.apply {
                    visibility = View.VISIBLE
                    text = "${leftTasks} left"
                }
            } else
                binding.tvTasksLeft.visibility = View.GONE
        }

        companion object{
            fun from(parent: ViewGroup) : RecyclerView.ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryListItemBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }
    }

    class AddCategoryViewHolder(val binding: CategoryAddItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: CategoryClickListener){
            binding.apply {
                this.clickListener = clickListener
                executePendingBindings()
            }
        }

        companion object{
            fun from(parent: ViewGroup) : RecyclerView.ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryAddItemBinding.inflate(layoutInflater, parent, false)
                return AddCategoryViewHolder(binding)
            }
        }
    }
}

class CategoryDiffCallback : DiffUtil.ItemCallback<DataItem>() {
    override fun areItemsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem.title == newItem.title
    }

    override fun areContentsTheSame(oldItem: DataItem, newItem: DataItem): Boolean {
        return oldItem == newItem
    }
}

sealed class DataItem {
    abstract val title: String

    data class CategoryItem(val category: Category): DataItem() {
        override val title = category.name
    }

    object AddCategory: DataItem() {
        override val title = "Add category"
    }
}