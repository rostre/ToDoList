package ro.twodoors.simplelist.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category_table ORDER BY category_name")
    fun getAllCategories() : LiveData<List<Category>>

    @Query("SELECT category_name FROM category_table ORDER BY category_name")
    fun getCategoryNames() : LiveData<List<String>>

    @Query("DELETE FROM category_table WHERE category_name = :categoryName")
    suspend fun deleteCategory(categoryName: String)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCategory(category: Category)

    @Update
    suspend fun updateCategory(category: Category)
}