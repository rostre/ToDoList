package ro.twodoors.todolist.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import ro.twodoors.todolist.R
import ro.twodoors.todolist.databinding.FragmentAddCategoryBinding
import ro.twodoors.todolist.obtainViewModel
import ro.twodoors.todolist.view.activity.MainActivity
import ro.twodoors.todolist.viewmodel.AddCategoryViewModel
import yuku.ambilwarna.AmbilWarnaDialog

class AddCategoryFragment : BottomSheetDialogFragment() {

    private var categoryColor : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val viewModel = (activity as MainActivity).obtainViewModel(AddCategoryViewModel::class.java)

        dialog?.setOnShowListener {bottomSheet ->
            val bottomSheetDialog = bottomSheet as BottomSheetDialog
            val sheetInternal: View = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(sheetInternal).state = BottomSheetBehavior.STATE_EXPANDED
        }

        val addCategoryBinding =
            DataBindingUtil.inflate<FragmentAddCategoryBinding>(inflater, R.layout.fragment_add_category, container, false)
        addCategoryBinding.lifecycleOwner = this

        categoryColor = ContextCompat.getColor(activity as MainActivity,R.color.colorPrimary)
        addCategoryBinding.txtColorPicker.setOnClickListener {
            openColorPicker(it)
        }

        addCategoryBinding.btnSaveCategory.setOnClickListener {
            viewModel.category.name = addCategoryBinding.etCategoryName.text.toString()

            val message = viewModel.saveCategory()
            if (message != null) {
                Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
            } else {
                viewModel.saveColorToSharedPref(activity as MainActivity, addCategoryBinding.etCategoryName.text.toString(), categoryColor)
                dismiss()
            }
        }

        addCategoryBinding.btnCancelCategory.setOnClickListener {
            dismiss()
        }

        return addCategoryBinding.root
    }

    private fun openColorPicker(view: View) {
        val colorPicker = AmbilWarnaDialog(activity, categoryColor, object : AmbilWarnaDialog.OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                categoryColor = color
                view.setBackgroundColor(categoryColor)
            }
        })
        colorPicker.show()
    }
}