package ro.twodoors.simplelist.model

import androidx.lifecycle.LiveData

interface TodoRepository {

    fun getAllTodos(): LiveData<List<Todo>>

    fun getAllTodosCount(): LiveData<Int>

    fun getTodosCompletedCount(): LiveData<Int>

    suspend fun completeTodo(id: Int, isChecked: Boolean)

    suspend fun insert(todo: Todo)

    suspend fun update(todo: Todo)

    suspend fun delete(todo: Todo)

    suspend fun deleteAll()

    suspend fun deleteCompletedTasks()
}