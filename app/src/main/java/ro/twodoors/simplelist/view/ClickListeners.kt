package ro.twodoors.simplelist.view

interface CategoryClickListener {

    fun onCategorySelected(title: String)

    fun addCategory()
}

interface TodoClickListener {
    fun onCheckboxSelected(id: Int, isChecked: Boolean)
}
