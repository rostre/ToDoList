package ro.twodoors.todolist.view.activity

import android.content.Context
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)
        setSupportActionBar(toolbar_category)

        toolbar_category.setNavigationOnClickListener { onBackPressed() }

        title = intent.getStringExtra("CAT")
        supportActionBar?.title = title
        val sharedPref = getSharedPreferences("CATEGORY", Context.MODE_PRIVATE)
        val color = sharedPref.getInt(title, Color.BLACK)
        window.statusBarColor = color
        toolbar_category.setBackgroundColor(color)

        viewModel = obtainViewModel(CategoryViewModel::class.java)

        val adapter = MainAdapter(this)
        rvTasksByCategory.adapter = adapter
        viewModel.allTodos.observe(this, Observer { todos ->
            if (title != "All Tasks"){
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (title != "All Tasks"){
            menuInflater.inflate(R.menu.menu_category, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteCategory -> {
                viewModel.deleteCategory(title)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
