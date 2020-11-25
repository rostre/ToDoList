package ro.twodoors.simplelist.ui.addtodo

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_add_task.*
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.utils.formatDate
import ro.twodoors.simplelist.utils.formatTime
import java.util.*

@AndroidEntryPoint
class AddTaskActivity : AppCompatActivity() {

    private val addTodoViewModel by viewModels<AddTodoViewModel>()
    private lateinit var spinnerAdapter: SpinnerCategoryAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_add_task)

        setupActionBar()
        setupClickListeners()
        subscribeUi()
        setupSpinner()
    }

    private fun subscribeUi(){
        addTodoViewModel.allCategories.observe(this, Observer { categoriesList ->
            if(categoriesList.isNullOrEmpty()){
                hideSpinner()
            } else {
                showCategories(categoriesList)
            }
        })
    }

    private fun setupSpinner(){
        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCategory = spinnerAdapter.getItem(position)
                if (selectedCategory != null)
                    addTodoViewModel.todo.category = selectedCategory
            }
        }
    }

    private fun setupClickListeners() {
        btnCreateTask.setOnClickListener{ createTask(it) }
        txtDueDate.setOnClickListener { showDatePicker() }
        txtTime.setOnClickListener { showTimePicker() }
        lblDueDate.setOnClickListener { showDatePicker() }
        lblTime.setOnClickListener { showTimePicker() }
    }

    private fun setupActionBar() {
        setSupportActionBar(toolbar_add_task)
        toolbar_add_task.setNavigationOnClickListener { onBackPressed() }
    }

    private fun createTask(view: View){
        addTodoViewModel.todo.apply {
            title = txtTitle.text.toString()
            description = txtDescription.text.toString()
        }
        val message = addTodoViewModel.save()
        when {
            message != null -> Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

            else -> finish()
        }
    }

    private fun showCategories(categoriesList : List<String>){
        showSpinner()
        spinnerAdapter = SpinnerCategoryAdapter(
            this,
            categoriesList
        )
        spinner.adapter = spinnerAdapter
    }

    private fun showSpinner(){
        txtNoCategories.visibility = View.GONE
        spinner.visibility = View.VISIBLE
    }

    private fun hideSpinner(){
        txtNoCategories.visibility = View.VISIBLE
        spinner.visibility = View.INVISIBLE
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
        lblTime.visibility = View.VISIBLE
    }

    private fun showDatePicker(){
        val calendar = Calendar.getInstance()
        addTodoViewModel.todo.dueDate?.let { calendar.timeInMillis = it }
        val datePicker =
            DatePickerDialog(
                this,
                DatePickerDialog.OnDateSetListener { _, year, month, day ->
                    txtDueDate.visibility = View.VISIBLE
                    lblDueDate.visibility = View.GONE
                    calendar.apply {
                        set(Calendar.YEAR, year)
                        set(Calendar.MONTH, month)
                        set(Calendar.DAY_OF_MONTH, day)
                    }
                    addTodoViewModel.todo.dueDate = calendar.timeInMillis
                    txtDueDate.text = formatDate(calendar.timeInMillis)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH) )


        datePicker.setOnCancelListener{
            hideDueDate()
            lblDueDate.visibility = View.VISIBLE
        }

        datePicker.datePicker.minDate = System.currentTimeMillis()
        datePicker.show()
    }

    private fun showTimePicker() {
        val calendar = Calendar.getInstance()
        addTodoViewModel.todo.dueTime?.let { calendar.timeInMillis = it }
        val timePicker =
            TimePickerDialog(
                this,
                TimePickerDialog.OnTimeSetListener { _, hour, minute ->
                    txtTime.visibility = View.VISIBLE
                    lblTime.visibility = View.GONE
                    calendar.apply {
                        set(Calendar.HOUR_OF_DAY, hour)
                        set(Calendar.MINUTE, minute)
                    }
                    addTodoViewModel.todo.dueTime = calendar.timeInMillis
                    txtTime.text = formatTime(calendar.timeInMillis)
                },
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                false)

        timePicker.setOnCancelListener {
            hideDueTime()
        }

        timePicker.show()
    }

}
