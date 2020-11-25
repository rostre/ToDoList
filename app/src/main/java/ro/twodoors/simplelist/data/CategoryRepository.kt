package ro.twodoors.simplelist.data

import androidx.lifecycle.LiveData

interface CategoryRepository {

    fun getAllCategories(): LiveData<List<Category>>

    fun getCategoryNames(): LiveData<List<String>>

    suspend fun insertCategory(category: Category)

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(categoryName: String)
}