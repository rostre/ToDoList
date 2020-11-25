package ro.twodoors.simplelist.ui.main

interface CategoryClickListener {

    fun navigateToCategoryDetail(title: String)

    fun navigateToAddCategory()
}

interface TodoClickListener {
    fun onCheckboxSelected(id: Int, isChecked: Boolean)
}
