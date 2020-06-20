package ro.twodoors.todolist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ro.twodoors.todolist.R
import ro.twodoors.todolist.databinding.ActivityCategoryBinding
import ro.twodoors.todolist.obtainViewModel
import ro.twodoors.todolist.utils.SwipeToDeleteCallback
import ro.twodoors.todolist.viewmodel.CategoryViewModel

class CategoryActivity : AppCompatActivity(), MainAdapter.OnClickListener {

    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val title = intent.getStringExtra("CAT")

        viewModel = obtainViewModel(CategoryViewModel::class.java)


        val adapter = MainAdapter(this)
        rvTasksByCategory.adapter = adapter
        viewModel.allTodos.observe(this, Observer { todos ->
            val result = todos
                .filter { it ->
                it.categoryName == title }
                .sortedBy { it ->
                it.title
            }
            adapter.submitList(result)
            txtAllTasks.text = result.count().toString()
        })

        val swipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTask = adapter.getTodoAtPosition(viewHolder.adapterPosition)
                viewModel.delete(swipedTask)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvTasksByCategory)
    }

    override fun onCheckboxChecked(id: Int, isChecked: Boolean) {
        viewModel.completeTodo(id, isChecked)
    }
}
