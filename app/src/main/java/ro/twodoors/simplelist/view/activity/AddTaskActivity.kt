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
        setSupportActionBar(toolbar_add_task)
        supportActionBar?.title = "Create New Task"
        toolbar_add_task.setNavigationOnClickListener { onBackPressed() }
        addTodoViewModel = obtainViewModel(AddTodoViewModel::class.java)

        btnCreateTask.setOnClickListener{
            addTodoViewModel.todo.title = txtTitle.text.toString()
            addTodoViewModel.todo.description = txtDescription.text.toString()

            val message = addTodoViewModel.save()
            if (message != null) {
                Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
            } else {
                finish()
            }
        }

        addTodoViewModel.allCategories.observe(this, Observer { categoriesList ->
            if(categoriesList.isNullOrEmpty()){
                btnAddCategory.visibility = View.VISIBLE
                spinner.visibility = View.INVISIBLE
            } else {
                btnAddCategory.visibility = View.GONE
                spinner.visibility = View.VISIBLE
                spinnerAdapter =
                    SpinnerCategoryAdapter(this, categoriesList)
                spinner.adapter = spinnerAdapter
            }
        })

        btnAddCategory.setOnClickListener {
            showAddCategoryFragment(this)
        }

        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = spinnerAdapter.getItem(position)
                if (item != null)
                    addTodoViewModel.todo.categoryName = item
            }
        }

        switcher.setOnCheckedChangeListener{ compoundButton, isChecked ->
            if (isChecked){
                val calendar = Calendar.getInstance()
                val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    //ivCalendar.visibility = View.VISIBLE
                    txtDueDate.visibility = View.VISIBLE
                    calendar.set(Calendar.YEAR, year)
                    calendar.set(Calendar.MONTH, month)
                    calendar.set(Calendar.DAY_OF_MONTH, day)
                    addTodoViewModel.todo.dueDate = calendar.timeInMillis
                    txtDueDate.text = convertLongToString(calendar.timeInMillis)
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) )

//                ivCalendar.setOnClickListener{
//                    val pickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
//                        txtDueDate.text = convertLongToString(calendar.timeInMillis)
//                    }, y , m, d )
//                    pickerDialog.show()
//                }

                datePicker.setOnCancelListener{
                    txtDueDate.text = ""
                    txtDueDate.visibility = View.GONE
                    //ivCalendar.visibility = View.GONE
                    switcher.isChecked = false
                }

                datePicker.datePicker.minDate = calendar.time.time

                datePicker.show()
            } else{
                //ivCalendar.visibility = View.GONE
                txtDueDate.text = ""
                txtDueDate.visibility = View.GONE
                addTodoViewModel.todo.dueDate = null
            }
        }

        switchTime.setOnCheckedChangeListener{ compoundButton, isChecked ->
            if (isChecked){
                val calendar = Calendar.getInstance()
                val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { picker, hour, minute ->
                    //ivTime.visibility = View.VISIBLE
                    txtTime.visibility = View.VISIBLE
                    calendar.set(Calendar.HOUR_OF_DAY, hour)
                    calendar.set(Calendar.MINUTE, minute)
                    //addViewModel.todo.dueDate = calendar.time
                    txtTime.text = SimpleDateFormat("HH:mm").format(calendar.time)
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false)
//                { view, year, month, day ->
//                    ivTime.visibility = View.VISIBLE
//                    txtTime.visibility = View.VISIBLE
//                    calendar.set(Calendar.YEAR, year)
//                    calendar.set(Calendar.MONTH, month)
//                    calendar.set(Calendar.DAY_OF_MONTH, day)
//                    addViewModel.todo.dueDate = calendar.timeInMillis
//                    txtTime.text = convertLongToString(calendar.timeInMillis)
//                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) )

                timePicker.setOnCancelListener{
                    txtTime.text = ""
                    txtTime.visibility = View.GONE
                    //ivTime.visibility = View.GONE
                    switchTime.isChecked = false
                }

                timePicker.show()
            } else{
                //ivTime.visibility = View.GONE
                txtTime.text = ""
                txtTime.visibility = View.GONE
                addTodoViewModel.todo.dueDate = null
            }
        }

    }

}
