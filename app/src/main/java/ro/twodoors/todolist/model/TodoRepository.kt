package ro.twodoors.todolist.model

import androidx.lifecycle.LiveData

interface TodoRepository {

    fun getAllTodos(): LiveData<List<Todo>>

    fun getAllTodosCount(): LiveData<Int>

    fun getTodosCompletedCount(): LiveData<Int>

    fun getAllCategories(): LiveData<List<Category>>

    fun getCategoryNames(): LiveData<List<String>>

    suspend fun completeTodo(id: Int, isChecked: Boolean)

    suspend fun insert(todo: Todo)

    suspend fun insertCategory(category: Category)

    suspend fun update(todo: Todo)

    suspend fun deleteCategory(categoryName: String)

    suspend fun delete(todo: Todo)

    suspend fun deleteAll()

    suspend fun deleteCompletedTasks()

    suspend fun updateCategory(category: Category)

}