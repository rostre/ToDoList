package ro.twodoors.simplelist.view.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_task.*
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.obtainViewModel
import ro.twodoors.simplelist.utils.convertLongToString
import ro.twodoors.simplelist.utils.showAddCategoryFragment
import ro.twodoors.simplelist.view.adapter.SpinnerCategoryAdapter
import ro.twodoors.simplelist.viewmodel.AddTodoViewModel
import java.text.SimpleDateFormat
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var addTodoViewModel: AddTodoViewModel
    private lateinit var spinnerAdapter: SpinnerCategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_add_task)

        setupActionBar()
        addTodoViewModel = obtainViewModel(AddTodoViewModel::class.java)

        btnCreateTask.setOnClickListener{ createTask(it) }
        btnAddCategory.setOnClickListener { showAddCategoryFragment(this) }
        txtDueDate.setOnClickListener { showDatePicker() }

        addTodoViewModel.allCategories.observe(this, Observer { categoriesList ->
            if(categoriesList.isNullOrEmpty()){
                showAddCategoryButton()
            } else {
                showCategories(categoriesList)
            }
        })

        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = spinnerAdapter.getItem(position)
                if (item != null)
                    addTodoViewModel.todo.category = item
            }
        }

        switcher.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                showDatePicker()
            } else{
                hideDueDate()
            }
        }

        switchTime.setOnCheckedChangeListener{ _, isChecked ->
            if (isChecked){
                showTimePicker()
            } else{
                hideDueTime()
            }
        }

    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_add_task)
        supportActionBar?.title = getString(R.string.create_new_task)
        toolbar_add_task.setNavigationOnClickListener { onBackPressed() }
    }

    private fun createTask(view: View){
        addTodoViewModel.todo.apply {
            title = txtTitle.text.toString()
            description = txtDescription.text.toString()
        }
        val message = addTodoViewModel.save()
        if (message != null) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
        } else {
            finish()
        }
    }

    private fun showAddCategoryButton(){
        btnAddCategory.visibility = View.VISIBLE
        spinner.visibility = View.INVISIBLE
    }

    private fun showCategories(categoriesList : List<String>){
        btnAddCategory.visibility = View.GONE
        spinner.visibility = View.VISIBLE

        spinnerAdapter = SpinnerCategoryAdapter(this, categoriesList)
        spinner.adapter = spinnerAdapter
    }

    private fun hideDueDate(){
        txtDueDate.apply {
            text = ""
            visibility = View.GONE
        }
        addTodoViewModel.todo.dueDate = null
    }

    private fun hideDueTime() {
        txtTime.text = ""
        txtTime.visibility = View.GONE
        addTodoViewModel.todo.dueDate = null
    }

    private fun showDatePicker(){
        val calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, year, month, day ->
            txtDueDate.visibility = View.VISIBLE
            calendar.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            addTodoViewModel.todo.dueDate = calendar.timeInMillis
            txtDueDate.text = convertLongToString(calendar.timeInMillis)
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) )


        datePicker.setOnCancelListener{
            txtDueDate.text = ""
            txtDueDate.visibility = View.GONE
            switcher.isChecked = false
        }

        datePicker.datePicker.minDate = calendar.time.time
        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        val timePicker =
            TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                txtTime.visibility = View.VISIBLE
                calendar.set(Calendar.HOUR_OF_DAY, hour)
                calendar.set(Calendar.MINUTE, minute)
                addTodoViewModel.todo.dueTime = calendar.timeInMillis
                txtTime.text = SimpleDateFormat("HH:mm").format(calendar.time)
            }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)

        timePicker.setOnCancelListener {
            txtTime.text = ""
            txtTime.visibility = View.GONE
            switchTime.isChecked = false
        }

        timePicker.show()
    }

}
