package ro.twodoors.todolist.view

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import ro.twodoors.todolist.R
import ro.twodoors.todolist.databinding.ActivityMainBinding
import ro.twodoors.todolist.obtainViewModel
import ro.twodoors.todolist.utils.SwipeToDeleteCallback
import ro.twodoors.todolist.viewmodel.TodoViewModel


class MainActivity : AppCompatActivity(), MainAdapter.OnClickListener {

    private lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_main)
        val binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        viewModel = obtainViewModel(TodoViewModel::class.java)

        val adapter = MainAdapter(this)
        recyclerView.adapter = adapter

        viewModel.allTodos.observe(this, Observer { todos ->
                binding.hasTasks = !todos.isNullOrEmpty()
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
//            val bundle = ActivityOptionsCompat.makeCustomAnimation(
//                this,
//                R.anim.left_to_right, R.anim.left_to_right
//            ).toBundle()
//            startActivity(intent,bundle)
            //overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
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
}
