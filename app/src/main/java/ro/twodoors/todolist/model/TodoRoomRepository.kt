package ro.twodoors.todolist.model

import androidx.lifecycle.LiveData

class TodoRoomRepository(private val todoDao: TodoDao) : TodoRepository {

    private var allTodos: LiveData<List<Todo>> = todoDao.getAll()

    override fun getAllTodos(): LiveData<List<Todo>> {
        return allTodos
    }

    override fun getAllTodosCount(): LiveData<Int> {
        return todoDao.getAllTodosCount()
    }

    override fun getTodosCompletedCount(): LiveData<Int> {
        return todoDao.getTodosCompletedCount()
    }

    override fun getCategoryNames(): LiveData<List<String>> {
        return todoDao.getCategoryNames()
    }

    override fun getAllCategories(): LiveData<List<Category>> {
        return todoDao.getAllCategories()
    }

    override fun getTasksForCategory(categoryName: String): LiveData<Int> {
        return todoDao.getTasksForCategory(categoryName)
    }

    override fun getCategories(): List<Category> {
        return todoDao.getCategories()
    }

    override suspend fun completeTodo(id: Int, isChecked: Boolean) {
        todoDao.completeTodo(id, isChecked)
    }

    override suspend fun insert(todo: Todo){
        todoDao.insert(todo)
    }

    override suspend fun insertCategory(category: Category) {
        todoDao.insertCategory(category)
    }

    override suspend fun update(todo: Todo){
        todoDao.update(todo)
    }

    override suspend fun delete(todo: Todo){
        todoDao.delete(todo)
    }

    override suspend fun deleteAll() {
        todoDao.deleteAll()
    }

    override suspend fun deleteCompletedTasks() {
        todoDao.deleteCompletedTasks()
    }

    override suspend fun updateCategory(category: Category) {
        todoDao.updateCategory(category)
    }
}