package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.model.Category
import ro.twodoors.simplelist.model.CategoryRepository
import ro.twodoors.simplelist.model.TodoRepository

class AddCategoryViewModel(private val categoryRepo: CategoryRepository) : ViewModel(){

    val category = Category("")

    fun saveCategory(): String? {
        if (category.name.isEmpty()) return "Category name is required"

        insertCategory(category.name)
        return null
    }

    private fun insertCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        categoryRepo.insertCategory(Category(categoryName))
    }

}