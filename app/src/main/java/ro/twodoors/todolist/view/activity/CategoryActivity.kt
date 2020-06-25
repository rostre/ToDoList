package ro.twodoors.todolist.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.*
import ro.twodoors.todolist.R
import ro.twodoors.todolist.obtainViewModel
import ro.twodoors.todolist.utils.SwipeToDeleteCallback
import ro.twodoors.todolist.view.adapter.MainAdapter
import ro.twodoors.todolist.viewmodel.CategoryViewModel

class CategoryActivity : AppCompatActivity(),
    MainAdapter.OnClickListener {

    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        val title = intent.getStringExtra("CAT")

        viewModel = obtainViewModel(CategoryViewModel::class.java)

        val adapter = MainAdapter(this)
        rvTasksByCategory.adapter = adapter
        viewModel.allTodos.observe(this, Observer { todos ->
            if (title != "ALL TASKS"){
                val result = todos
                    .filter { it ->
                        it.categoryName == title }
                    .sortedBy { it ->
                        it.title
                    }
                adapter.submitList(result)
                txtAllTasks.text = result.count().toString()
            } else{
                adapter.submitList(todos)
                txtAllTasks.text = todos.count().toString()
            }


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
