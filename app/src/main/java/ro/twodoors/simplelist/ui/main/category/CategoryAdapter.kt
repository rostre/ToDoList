package ro.twodoors.simplelist.ui.main.category

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ro.twodoors.simplelist.data.Category
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.ui.main.CategoryClickListener
import java.lang.ClassCastException

private const val ITEM_VIEW_TYPE_ITEM = 0
private const val ITEM_VIEW_TYPE_ADD_CATEGORY = 1

class CategoryAdapter(private val onClickListener: CategoryClickListener)
    : ListAdapter<DataItem, RecyclerView.ViewHolder>(
    CategoryDiffCallback()
) {

    private var todos: List<Todo> = listOf()
    private val adapterScope = CoroutineScope(Dispatchers.Default)


    fun setTodos(todos: List<Todo>){
        this.todos = todos
        notifyDataSetChanged()
    }

    fun populateCategories(list : List<Category>?){
        adapterScope.launch {
            val items = if(list.isNullOrEmpty())
                            listOf(DataItem.AddCategory)
                        else
                            list.map {
                                DataItem.CategoryItem(
                                    it
                                )
                            } + listOf(DataItem.AddCategory)

            withContext(Dispatchers.Main){
                submitList(items)
            }
        }
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
                    setupTodos(todos)
                }
            }

            is AddCategoryViewHolder -> {
                holder.bind(onClickListener)
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