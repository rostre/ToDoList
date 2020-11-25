package ro.twodoors.simplelist.data

import androidx.lifecycle.LiveData
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao) : TodoRepository {

    override fun getAllTodos(): LiveData<List<Todo>> = todoDao.getAll()

    override fun getAllTodosCount(): LiveData<Int> = todoDao.getAllTodosCount()

    override fun getTodosCompletedCount(): LiveData<Int> =  todoDao.getTodosCompletedCount()

    override suspend fun completeTodo(id: Int, isChecked: Boolean) = todoDao.completeTodo(id, isChecked)

    override suspend fun insert(todo: Todo) = todoDao.insert(todo)

    override suspend fun update(todo: Todo) = todoDao.update(todo)

    override suspend fun delete(todo: Todo) = todoDao.delete(todo)

    override suspend fun deleteAll() = todoDao.deleteAll()

    override suspend fun deleteCompletedTasks() = todoDao.deleteCompletedTasks()
}