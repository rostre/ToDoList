package ro.twodoors.todolist.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ro.twodoors.todolist.R
import ro.twodoors.todolist.databinding.ActivityMainBinding
import ro.twodoors.todolist.obtainViewModel
import ro.twodoors.todolist.utils.SwipeToDeleteCallback
import ro.twodoors.todolist.view.adapter.CategoryAdapter
import ro.twodoors.todolist.view.adapter.MainAdapter
import ro.twodoors.todolist.view.OnClickListener
import ro.twodoors.todolist.view.fragment.AddCategoryFragment
import ro.twodoors.todolist.viewmodel.TodoViewModel

class MainActivity : AppCompatActivity(),
    MainAdapter.OnClickListener,
    OnClickListener {

    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = obtainViewModel(TodoViewModel::class.java)

        val adapter = MainAdapter(this)
        recyclerView.adapter = adapter
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && fab.visibility == View.VISIBLE){
                    fab.hide()
                } else if (dy < 0 && fab.visibility != View.VISIBLE)
                    fab.show()
            }
        })

        val categoryAdapter =
            CategoryAdapter(this)
        recyclerViewCategory.adapter = categoryAdapter

        viewModel.allCategories.observe(this, Observer { categories ->
            categoryAdapter.populateCategories(categories)
        })

        viewModel.allTodos.observe(this, Observer { todos ->
                binding.hasTasks = !todos.isNullOrEmpty()
                categoryAdapter.setTodos(todos)
                adapter.submitList(todos)
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
                val swipedTask = adapter.getTodoAtPosition(viewHolder.adapterPosition)
                viewModel.delete(swipedTask)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

    }

    override fun onCheckboxChecked(id: Int, isChecked: Boolean) {
        viewModel.completeTodo(id, isChecked)
    }

    override fun onCategorySelected(title: String) {
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra("CAT", title)
        startActivity(intent)
    }

    override fun addCategory() {
        showAddCategoryFragment()
//        val intent = Intent(this, AddCategoryActivity::class.java)
//        startActivity(intent)
//        val input = TextInputEditText(this)
//        val layoutParams = LinearLayout.LayoutParams(
//            LinearLayout.LayoutParams.MATCH_PARENT,
//            LinearLayout.LayoutParams.WRAP_CONTENT
//        )
//        input.layoutParams = layoutParams
//        input.hint = "Category Name"
//
//        AlertDialog.Builder(this)
//            .setTitle("Add Category")
//            .setView(input)
//            .setPositiveButton("Save") { _, _ ->
//                val name = input.text.toString()
//                viewModel.insertCategory(name)
//            }
//            .setNegativeButton("Cancel", null)
//            .show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteCompletedTasks -> {
                viewModel.deleteCompletedTasks()
            }

            R.id.deleteAll -> {
                viewModel.deleteAll()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showAddCategoryFragment() {
        val bottomSheetFragment = AddCategoryFragment()
        bottomSheetFragment.show(supportFragmentManager, bottomSheetFragment.tag)
    }
}
