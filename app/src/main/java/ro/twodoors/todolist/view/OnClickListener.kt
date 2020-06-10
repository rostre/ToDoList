package ro.twodoors.todolist.view

interface OnClickListener {
    fun onCategorySelected(title: String)

    fun addCategory()
}