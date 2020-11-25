package ro.twodoors.simplelist.ui.addcategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.databinding.FragmentAddCategoryBinding
import ro.twodoors.simplelist.utils.SharedPrefsHelper.Companion.saveColorToPref
import yuku.ambilwarna.AmbilWarnaDialog

@AndroidEntryPoint
class AddCategoryFragment : BottomSheetDialogFragment() {

    private var categoryColor : Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val addCategoryViewModel by viewModels<AddCategoryViewModel>()
        categoryColor = ContextCompat.getColor(requireActivity(), R.color.colorPrimary)

        val addCategoryBinding =
            DataBindingUtil.inflate<FragmentAddCategoryBinding>(inflater, R.layout.fragment_add_category, container, false)
                .apply {
                    lifecycleOwner = this@AddCategoryFragment
                    txtColorPicker.setOnClickListener { openColorPicker(it) }
                    btnCancelCategory.setOnClickListener { dismiss() }
                    btnSaveCategory.setOnClickListener {
                        addCategoryViewModel.category.name = etCategoryName.text.toString()
                        val message = addCategoryViewModel.saveCategory()
                        when {
                            message != null -> {
                                etCategoryName.error = message
                            }
                            else -> {
                                saveColorToPref(requireContext(), etCategoryName.text.toString(), categoryColor)
                                dismiss()
                            }
                        }
                    }
                }

        setupShowDialog()

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
        val colorPicker = AmbilWarnaDialog(requireContext(), categoryColor, object : AmbilWarnaDialog.OnAmbilWarnaListener{

            override fun onCancel(dialog: AmbilWarnaDialog?) {}

            override fun onOk(dialog: AmbilWarnaDialog?, color: Int) {
                categoryColor = color
                val drawable = view.background
                drawable.mutate()
                drawable.setTint(categoryColor)

            }
        })
        colorPicker.show()
    }
}