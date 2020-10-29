package ro.twodoors.simplelist.view.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.databinding.ActivityMainBinding
import ro.twodoors.simplelist.obtainViewModel
import ro.twodoors.simplelist.utils.Constants.Companion.CATEGORY_TITLE_KEY
import ro.twodoors.simplelist.utils.SwipeToDeleteCallback
import ro.twodoors.simplelist.utils.showAddCategoryFragment
import ro.twodoors.simplelist.view.adapter.CategoryAdapter
import ro.twodoors.simplelist.view.adapter.TodoAdapter
import ro.twodoors.simplelist.view.CategoryClickListener
import ro.twodoors.simplelist.view.TodoClickListener
import ro.twodoors.simplelist.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity(), TodoClickListener, CategoryClickListener {

    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        viewModel = obtainViewModel(TodoViewModel::class.java)
        setupActionBar()

        val todoAdapter = TodoAdapter(this)
        recyclerView.adapter = todoAdapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE){
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE)
                    fab.show()
            }
        })

        val categoryAdapter = CategoryAdapter(this)
        recyclerViewCategory.adapter = categoryAdapter

        viewModel.allCategories.observe(this, Observer { categories ->
            categoryAdapter.populateCategories(categories)
        })

        viewModel.allTodos.observe(this, Observer { todos ->
                binding.hasTasks = !todos.isNullOrEmpty()
                categoryAdapter.setTodos(todos)
                todoAdapter.submitList(todos)
        })

        viewModel.todosCompletedCount.observe(this, Observer {count ->
                txtCompleted.text = getString(R.string.compl, count)
        })

        viewModel.allTodosCount.observe(this, Observer {count ->
                txtAll.text = getString(R.string.all, count)
        })


        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }

        val swipeHandler = object : SwipeToDeleteCallback(this@MainActivity){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTask = todoAdapter.getTodoAtPosition(viewHolder.adapterPosition)
                viewModel.delete(swipedTask)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_main)
        window.statusBarColor = Color.BLACK
        toolbar_main.setBackgroundColor(Color.BLACK)
    }

    override fun onCheckboxSelected(id: Int, isChecked: Boolean) {
        viewModel.completeTodo(id, isChecked)
    }

    override fun onCategorySelected(title: String) {
        val intent = Intent(this, CategoryDetailActivity::class.java)
        intent.putExtra(CATEGORY_TITLE_KEY, title)
        startActivity(intent)
    }

    override fun addCategory() {
        showAddCategoryFragment(this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteCompletedTasks -> {
                viewModel.deleteCompletedTasks()
                return true
            }

            R.id.deleteAll -> {
                viewModel.deleteAll()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
