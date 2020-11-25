package ro.twodoors.simplelist.ui.categorydetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_category_detail.*
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.utils.Constants.Companion.CATEGORY_TITLE_KEY
import ro.twodoors.simplelist.utils.SharedPrefsHelper.Companion.getColorFromPref
import ro.twodoors.simplelist.utils.SharedPrefsHelper.Companion.removeColorFromPref
import ro.twodoors.simplelist.utils.SwipeToDeleteCallback
import ro.twodoors.simplelist.ui.main.TodoClickListener

@AndroidEntryPoint
class CategoryDetailActivity : AppCompatActivity(),
    TodoClickListener {

    private val categoryViewModel by viewModels<CategoryDetailViewModel>()
    private lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_category_detail)

        setupActionBar()
        setupColors()
        setupAdapter()
    }

    private fun setupActionBar(){
        setSupportActionBar(toolbar_category)
        toolbar_category.setNavigationOnClickListener { onBackPressed() }
        title = intent.getStringExtra(CATEGORY_TITLE_KEY) ?: ""
        supportActionBar?.title = title
    }

    private fun setupColors(){
        val color = getColorFromPref(this, title)
        window.statusBarColor = color
        toolbar_category.setBackgroundColor(color)
    }

    private fun setupAdapter(){
        val categoryDetailAdapter = CategoryDetailAdapter(this)
        rvTasksByCategory.adapter = categoryDetailAdapter
        categoryViewModel.allTodos.observe(this, Observer { todos ->
            val result = todos.filter { it -> it.category == title }
            categoryDetailAdapter.submitList(result)
            txtAllTasks.text = result.count().toString()
        })

        val swipeHandler = object : SwipeToDeleteCallback(this){
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val swipedTodo = categoryDetailAdapter.getTodoAtPosition(viewHolder.adapterPosition)
                categoryViewModel.deleteTodo(swipedTodo)
            }
        }

        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(rvTasksByCategory)
    }

    override fun onCheckboxSelected(id: Int, isChecked: Boolean) {
        categoryViewModel.completeTodo(id, isChecked)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_category, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.deleteCategory -> {

                if (hasTasks()){
                    val builder = AlertDialog.Builder(this)
                    builder.setMessage(getString(R.string.question_delete_category, title))
                        .setPositiveButton(getString(R.string.delete)) { _, _ ->
                            deleteCategory()
                        }
                        .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                            dialog.dismiss()
                        }
                        .show()
                }
                else{
                    deleteCategory()
                }

                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteCategory(){
        categoryViewModel.deleteCategory(title)
        removeColorFromPref(this, title)
        finish()
    }

    private fun hasTasks() : Boolean {
        var hasTasks = false
        categoryViewModel.allTodos.observe(this, Observer {tasks ->
            val results = tasks.filter { it -> it.category == title }
            hasTasks = results.count() > 0
        })
        return hasTasks
    }
}
