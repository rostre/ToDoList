package ro.twodoors.simplelist.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.ui.addtodo.AddTaskActivity
import ro.twodoors.simplelist.databinding.ActivityMainBinding
import ro.twodoors.simplelist.utils.Constants.Companion.CATEGORY_TITLE_KEY
import ro.twodoors.simplelist.utils.SwipeToDeleteCallback
import ro.twodoors.simplelist.ui.addcategory.AddCategoryFragment
import ro.twodoors.simplelist.ui.categorydetail.CategoryDetailActivity
import ro.twodoors.simplelist.ui.main.category.CategoryAdapter
import ro.twodoors.simplelist.ui.main.todo.TodoAdapter

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),
    TodoClickListener,
    CategoryClickListener {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)

        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        setupActionBar()
        setupScrollListener()
        setupFabNavigation()

        val todoAdapter = TodoAdapter(this)
        recyclerView.adapter = todoAdapter

        setupSwipeToDelete(todoAdapter)

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
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_main)
    }

    private fun setupScrollListener()  {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE){
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE)
                    fab.show()
            }
        })
    }

    private fun setupFabNavigation(){
        fab.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCheckboxSelected(id: Int, isChecked: Boolean) {
        viewModel.completeTodo(id, isChecked)
    }

    override fun navigateToCategoryDetail(title: String) {
        val intent = Intent(this, CategoryDetailActivity::class.java)
        intent.putExtra(CATEGORY_TITLE_KEY, title)
        startActivity(intent)
    }

    override fun navigateToAddCategory() {
        val bottomSheetFragment = AddCategoryFragment()
        bottomSheetFragment.show((this as FragmentActivity).supportFragmentManager, bottomSheetFragment.tag)
    }

    private fun setupSwipeToDelete(todoAdapter: TodoAdapter){
        val swipeHandler = object : SwipeToDeleteCallback(this@MainActivity){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTask = todoAdapter.getTodoAtPosition(viewHolder.adapterPosition)
                viewModel.delete(swipedTask)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_options, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteCompletedTasks -> {

                if (hasCompletedTasks()){
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(getString(R.string.question_delete_tasks_completed))
                        .setPositiveButton(getString(R.string.delete)) { _, _ ->
                            viewModel.deleteCompletedTasks()
                        }
                        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }

                return true
            }

            R.id.deleteAll -> {

                if (hasTasks()){
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(getString(R.string.question_delete_all_tasks))
                        .setPositiveButton(getString(R.string.delete)) { _, _ ->
                            viewModel.deleteAll()
                        }
                        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun hasCompletedTasks() : Boolean {
        var hasCompletedTasks = false
        viewModel.todosCompletedCount.observe(this, Observer {count ->
            if (count > 0)
                hasCompletedTasks = true
        })
        return hasCompletedTasks
    }

    private fun hasTasks() : Boolean {
        var hasTasks = false
        viewModel.allTodosCount.observe(this, Observer {count ->
            if (count > 0)
                hasTasks = true
        })
        return hasTasks
    }

}
