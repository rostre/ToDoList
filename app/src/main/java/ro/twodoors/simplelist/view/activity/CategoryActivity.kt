package ro.twodoors.simplelist.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_category.*
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.obtainViewModel
import ro.twodoors.simplelist.utils.Constants.Companion.CATEGORY_TITLE_KEY
import ro.twodoors.simplelist.utils.SharedPrefsHelper
import ro.twodoors.simplelist.utils.SwipeToDeleteCallback
import ro.twodoors.simplelist.view.adapter.TodoAdapter
import ro.twodoors.simplelist.viewmodel.CategoryViewModel

class CategoryActivity : AppCompatActivity(),
    TodoAdapter.OnClickListener {

    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category)

        categoryViewModel = obtainViewModel(CategoryViewModel::class.java)
        setupActionBar()
        setupColors()
        setupAdapter()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_category)
        toolbar_category.setNavigationOnClickListener { onBackPressed() }
        title = intent.getStringExtra(CATEGORY_TITLE_KEY)
        supportActionBar?.title = title
    }

    private fun setupColors(){
        val color = SharedPrefsHelper.getColorFromPref(this, title)
        window.statusBarColor = color
        toolbar_category.setBackgroundColor(color)
    }

    private fun setupAdapter(){
        val adapter = TodoAdapter(this)
        rvTasksByCategory.adapter = adapter
        categoryViewModel.allTodos.observe(this, Observer { todos ->
            val result = todos.filter { it -> it.categoryName == title }
                .sortedBy { it -> it.title }
            adapter.submitList(result)
            txtAllTasks.text = result.count().toString()
        })

        val swipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTodo = adapter.getTodoAtPosition(viewHolder.adapterPosition)
                categoryViewModel.deleteTodo(swipedTodo)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvTasksByCategory)
    }

    override fun onCheckboxChecked(id: Int, isChecked: Boolean) {
        categoryViewModel.completeTodo(id, isChecked)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_category, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteCategory -> {
                categoryViewModel.deleteCategory(title)
                SharedPrefsHelper.removeColorFromPref(this, title)
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
