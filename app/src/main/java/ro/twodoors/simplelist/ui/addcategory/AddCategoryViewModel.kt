package ro.twodoors.simplelist.ui.addcategory

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.data.Category
import ro.twodoors.simplelist.data.CategoryRepository

class AddCategoryViewModel @ViewModelInject constructor(
    private val categoryRepository: CategoryRepository) : ViewModel(){

    val category = Category("")

    fun saveCategory(): String? {
        if (category.name.isEmpty())
            return "Category name is required"

        insertCategory(category.name)
        return null
    }

    private fun insertCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        categoryRepository.insertCategory(Category(categoryName))
    }

}