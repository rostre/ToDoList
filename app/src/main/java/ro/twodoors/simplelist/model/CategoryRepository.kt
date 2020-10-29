package ro.twodoors.simplelist.model

import androidx.lifecycle.LiveData

interface CategoryRepository {

    fun getAllCategories(): LiveData<List<Category>>

    fun getCategoryNames(): LiveData<List<String>>

    suspend fun insertCategory(category: Category)

    suspend fun deleteCategory(categoryName: String)

    suspend fun updateCategory(category: Category)
}