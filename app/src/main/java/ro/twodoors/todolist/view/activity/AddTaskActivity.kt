package ro.twodoors.todolist.view.activity

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_task.*
import ro.twodoors.todolist.R
import ro.twodoors.todolist.obtainViewModel
import ro.twodoors.todolist.view.adapter.SpinnerCategoryAdapter
import ro.twodoors.todolist.viewmodel.AddViewModel
import java.util.*

class AddTaskActivity : AppCompatActivity() {

    private lateinit var addViewModel: AddViewModel
    private lateinit var spinnerAdapter: SpinnerCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_add_task)
        addViewModel = obtainViewModel(AddViewModel::class.java)

        btnSave.setOnClickListener{
            addViewModel.todo.title = txtTitle.text.toString()
            addViewModel.todo.description = txtDescription.text.toString()
            //var ss = spinner.selectedItem as String
            //addViewModel.todo.categoryName = spinner.selectedItem.toString().replace("\n","")
            //addViewModel.todo.categoryName = ss.replace("\n","")
            if (switcher.isChecked)
                addViewModel.todo.dueDate = txtDueDate.text.toString().toLong()

            val message = addViewModel.save()
            if (message != null) {
                Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
            } else {
                finish()
            }
        }

        addViewModel.allCategories.observe(this, Observer { categoriesList ->
            spinnerAdapter =
                SpinnerCategoryAdapter(
                    this,
                    categoriesList
                )
            spinner.adapter = spinnerAdapter
        })

        spinner.onItemSelectedListener = object  : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val item = spinnerAdapter.getItem(position)
                addViewModel.todo.categoryName = item!!
                //Toast.makeText(this@AddTaskActivity, "selected ${item?.categoryName}", Toast.LENGTH_SHORT).show()
            }
        }

        switcher.setOnCheckedChangeListener{ compoundButton, isChecked ->
            if (isChecked){
                val c = Calendar.getInstance()
                val y = c.get(Calendar.YEAR)
                val m = c.get(Calendar.MONTH)
                val d = c.get(Calendar.DAY_OF_MONTH)
                val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, day ->
                    ivCalendar.visibility = View.VISIBLE
                    txtDueDate.visibility = View.VISIBLE
                    txtDueDate.text = "${day} - ${month} - ${year}"
                }, y , m, d )

                datePicker.setOnCancelListener{
                    txtDueDate.text = ""
                    txtDueDate.visibility = View.GONE
                    ivCalendar.visibility = View.GONE
                    switcher.isChecked = false
                }

                datePicker.show()
            } else{
                ivCalendar.visibility = View.GONE
                txtDueDate.text = ""
                txtDueDate.visibility = View.GONE
            }
        }

    }


}
