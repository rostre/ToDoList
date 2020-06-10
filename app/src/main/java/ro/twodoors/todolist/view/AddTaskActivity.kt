package ro.twodoors.todolist.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_add_task.*
import ro.twodoors.todolist.R
import ro.twodoors.todolist.obtainViewModel
import ro.twodoors.todolist.utils.Priority
import ro.twodoors.todolist.utils.intToPriority
import ro.twodoors.todolist.viewmodel.AddViewModel

class AddTaskActivity : AppCompatActivity() {

    private lateinit var addViewModel: AddViewModel
    private var priority: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView( R.layout.activity_add_task)
        addViewModel = obtainViewModel(AddViewModel::class.java)

        btnSave.setOnClickListener{
            addViewModel.todo.title = txtTitle.text.toString()
            addViewModel.todo.description = txtDescription.text.toString()
            addViewModel.todo.priority = intToPriority(priority)

            val message = addViewModel.save()
            if (message != null) {
                Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
            } else {
                finish()
            }
        }
        setupPicker()
    }

    private fun setupPicker(){
        priority_picker.minValue = 0
        priority_picker.maxValue = 3

        val pickerValues = arrayOf(Priority.DEFAULT.name, Priority.LOW.name, Priority.MEDIUM.name, Priority.HIGH.name)
        priority_picker.displayedValues = pickerValues
        priority_picker.setOnValueChangedListener{ numberPicker, i, i2 ->
            priority = numberPicker.value
            Log.d("setupPicker()", "value selected: $priority")
        }
    }
}
