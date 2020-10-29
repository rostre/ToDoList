package ro.twodoors.simplelist.model

import androidx.lifecycle.LiveData

class CategoryRepositoryImpl(private val categoryDao: CategoryDao) : CategoryRepository {

    override suspend fun updateCategory(category: Category) =
        categoryDao.updateCategory(category)


    override suspend fun deleteCategory(categoryName: String) =
        categoryDao.deleteCategory(categoryName)


    override suspend fun insertCategory(category: Category) =
        categoryDao.insertCategory(category)


    override fun getCategoryNames(): LiveData<List<String>> =
        categoryDao.getCategoryNames()


    override fun getAllCategories(): LiveData<List<Category>> =
        categoryDao.getAllCategories()
}