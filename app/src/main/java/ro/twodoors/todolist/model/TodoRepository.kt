package ro.twodoors.todolist.model

import androidx.lifecycle.LiveData

interface TodoRepository {

    fun getAllTodos(): LiveData<List<Todo>>

    fun getAllTodosCount(): LiveData<Int>

    fun getTodosCompletedCount(): LiveData<Int>

    fun getAllCategories(): LiveData<List<Category>>

    fun getTasksByCategory(categoryName: String) : LiveData<List<Todo>>

    suspend fun completeTodo(id: Int, isChecked: Boolean)

    suspend fun insert(todo: Todo)

    suspend fun insertCategory(category: Category)

    suspend fun update(todo: Todo)

    suspend fun delete(todo: Todo)

    suspend fun deleteAll()

    suspend fun deleteCompletedTasks()
}