package ro.twodoors.todolist.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.todolist.model.Category
import ro.twodoors.todolist.model.TodoRepository

class AddCategoryViewModel(private val repository: TodoRepository) : ViewModel(){

    val category = Category("")

    fun saveCategory(): String? {
        if (category.name.isEmpty()) return "Category name is required"

        insertCategory(category.name)
        return null
    }

    fun insertCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        repository.insertCategory(Category(categoryName))
    }

    fun saveColorToSharedPref(activity: Activity, key: String, value: Int ) {
        val sharedPref = activity.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()){
            putInt(key, value)
            apply()
        }
    }
}