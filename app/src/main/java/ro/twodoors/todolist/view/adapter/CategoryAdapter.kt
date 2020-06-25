package ro.twodoors.todolist.view.adapter

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ro.twodoors.todolist.databinding.CategoryAddItemBinding
import ro.twodoors.todolist.databinding.CategoryAllTasksItemBinding
import ro.twodoors.todolist.databinding.CategoryListItemBinding
import ro.twodoors.todolist.model.Category
import ro.twodoors.todolist.model.Todo
import ro.twodoors.todolist.view.OnClickListener
import java.lang.ClassCastException

private val ITEM_VIEW_TYPE_ALL_TASKS = 0
private val ITEM_VIEW_TYPE_ITEM = 1
private val ITEM_VIEW_TYPE_ADD_CATEGORY = 2


class CategoryAdapter(val onClickListener: OnClickListener) : ListAdapter<DataItem, RecyclerView.ViewHolder>(
    CategoryDiffCallback()
) {
    var tasks: List<Todo> = listOf()
    private val adapterScope = CoroutineScope(Dispatchers.Default)

    fun populateCategories(list : List<Category>?){
        adapterScope.launch {
            val items = if(list.isNullOrEmpty())
                            listOf(DataItem.AddCategory)
                        else
                            listOf(DataItem.AllTasks) + list.map { DataItem.CategoryItem(it) } + listOf(DataItem.AddCategory)

            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
    }

    fun setTodos(tasks: List<Todo>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

    class CategoryViewHolder(val binding: CategoryListItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(item: Category, clickListener: OnClickListener){
            binding.category = item
            val sharedPref = itemView.context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE)
            binding.cardCategory.setCardBackgroundColor(sharedPref.getInt(item.name, 0))
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        fun setupTasks(tasks: List<Todo>){
            val result = tasks
                .filter { it ->
                    it.categoryName == binding.category?.name }
            binding.tvCategoryTasks.text = result.count().toString()
        }

        companion object{
            fun from(parent: ViewGroup) : RecyclerView.ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryListItemBinding.inflate(layoutInflater, parent, false)
                return CategoryViewHolder(binding)
            }
        }

        fun getColorFromSharedPref(activity: Activity, key: String) : Int {
            val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return 0
            return sharedPref.getInt(key, 0)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_VIEW_TYPE_ALL_TASKS -> AllTasksViewHolder.from(parent)

            ITEM_VIEW_TYPE_ITEM -> CategoryViewHolder.from(parent)

            ITEM_VIEW_TYPE_ADD_CATEGORY -> AddCategoryViewHolder.from(parent)

            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)){
            is DataItem.AllTasks -> ITEM_VIEW_TYPE_ALL_TASKS
            is DataItem.CategoryItem -> ITEM_VIEW_TYPE_ITEM
            is DataItem.AddCategory -> ITEM_VIEW_TYPE_ADD_CATEGORY
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is CategoryViewHolder -> {
                val item = getItem(position) as DataItem.CategoryItem
                holder.bind(item.category, onClickListener)
                holder.setupTasks(tasks)
            }

            is AllTasksViewHolder -> {
                holder.bind("ALL TASKS", onClickListener )
            }

            is AddCategoryViewHolder -> {
                holder.bind(onClickListener)
            }
        }
    }

    class AllTasksViewHolder(val binding: CategoryAllTasksItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(title: String, clickListener: OnClickListener){
            binding.title = title
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup) : RecyclerView.ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = CategoryAllTasksItemBinding.inflate(layoutInflater, parent, false)
                return AllTasksViewHolder(binding)
            }
        }
    }

    class AddCategoryViewHolder(val binding: CategoryAddItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(clickListener: OnClickListener){
            binding.clickListener = clickListener
            binding.executePendingBindings()
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

    object AllTasks: DataItem() {
        override val title = "All tasks"
    }

    object AddCategory: DataItem() {
        override val title = "Add category"
    }
}