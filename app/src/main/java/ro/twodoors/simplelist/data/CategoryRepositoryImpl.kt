package ro.twodoors.simplelist.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val categoryDao: CategoryDao) : CategoryRepository {

    override fun getAllCategories(): LiveData<List<Category>> =
        categoryDao.getAllCategories()

    override fun getCategoryNames(): LiveData<List<String>> =
        categoryDao.getCategoryNames()

    override suspend fun insertCategory(category: Category) =
        categoryDao.insertCategory(category)

    override suspend fun updateCategory(category: Category) =
        categoryDao.updateCategory(category)


    override suspend fun deleteCategory(categoryName: String) =
        categoryDao.deleteCategory(categoryName)
}