package ro.twodoors.simplelist.view.fragment

import android.app.Activity
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
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.databinding.FragmentAddCategoryBinding
import ro.twodoors.simplelist.obtainViewModel
import ro.twodoors.simplelist.utils.SharedPrefsHelper
import ro.twodoors.simplelist.utils.SharedPrefsHelper.Companion.saveColorToPref
import ro.twodoors.simplelist.viewmodel.AddCategoryViewModel
import yuku.ambilwarna.AmbilWarnaDialog

class AddCategoryFragment(val activity: Activity) : BottomSheetDialogFragment() {

    private var categoryColor : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val addCategoryViewModel = activity.obtainViewModel(AddCategoryViewModel::class.java)

        setupShowDialog()
        //categoryColor = ContextCompat.getColor( activity, R.color.colorPrimary)
        categoryColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimary)

        val addCategoryBinding =
            DataBindingUtil.inflate<FragmentAddCategoryBinding>(inflater, R.layout.fragment_add_category, container, false).apply {

            lifecycleOwner = this@AddCategoryFragment

            txtColorPicker.setOnClickListener { openColorPicker(it) }

            btnSaveCategory.setOnClickListener {
                addCategoryViewModel.category.name = etCategoryName.text.toString()
                val message = addCategoryViewModel.saveCategory()
                if (message != null) {
                    Snackbar.make(it, message, Snackbar.LENGTH_LONG).show()
                } else {
                    //SharedPrefsHelper.saveColorToPref(activity, etCategoryName.text.toString(), categoryColor)
                    saveColorToPref(requireContext(), etCategoryName.text.toString(), categoryColor)

                    dismiss()
                }
            }

            btnCancelCategory.setOnClickListener {
                dismiss()
            }
        }

        return addCategoryBinding.root
    }

    private fun setupShowDialog(){
        dialog?.setOnShowListener { bottomSheet ->
            val bottomSheetDialog = bottomSheet as BottomSheetDialog
            val sheetInternal: View = bottomSheetDialog.findViewById(com.google.android.material.R.id.design_bottom_sheet)!!
            BottomSheetBehavior.from(sheetInternal).state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun openColorPicker(view: View) {
        //val colorPicker = AmbilWarnaDialog(activity, categoryColor, object : AmbilWarnaDialog.OnAmbilWarnaListener{
            val colorPicker = AmbilWarnaDialog(requireContext(), categoryColor, object : AmbilWarnaDialog.OnAmbilWarnaListener{
            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                categoryColor = color
                view.setBackgroundColor(categoryColor)
            }
        })
        colorPicker.show()
    }
}