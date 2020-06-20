package ro.twodoors.todolist.view

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_add_category.*
import ro.twodoors.todolist.R
import yuku.ambilwarna.AmbilWarnaDialog

class AddCategoryActivity : AppCompatActivity() {

    private var categoryColor : Int = 0
    private var savedColor : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        categoryColor = ContextCompat.getColor(this,R.color.colorPrimary)
        ivColorPicker.setOnClickListener{
            openColorPicker()
        }

        btnSave.setOnClickListener {
            val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return@setOnClickListener
            with(sharedPref.edit()){
                putInt(txtCategoryName.text.toString(), categoryColor)
                apply()
            }
        }

        getColorFromSharedPref()
    }

    private fun getColorFromSharedPref() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        savedColor = sharedPref.getInt("AAA", 0)
    }

    private fun openColorPicker() {
        val colorPicker = AmbilWarnaDialog(this, categoryColor, object : AmbilWarnaDialog.OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                categoryColor = color
                txtColor.setBackgroundColor(categoryColor)
            }
        })
        colorPicker.show()
    }
}
