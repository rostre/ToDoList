package ro.twodoors.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.todolist.model.Category
import ro.twodoors.todolist.model.Todo
import ro.twodoors.todolist.model.TodoRepository

class TodoViewModel(private val repository: TodoRepository) : ViewModel()   {

    val allTodos : LiveData<List<Todo>> = repository.getAllTodos()

    val allCategories : LiveData<List<Category>> = repository.getAllCategories()

    val allTodosCount : LiveData<Int> = repository.getAllTodosCount()

    val todosCompletedCount : LiveData<Int> = repository.getTodosCompletedCount()

    fun delete(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(todo)
    }

    fun deleteAll() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteAll()
    }

    fun deleteCompletedTasks() = viewModelScope.launch(Dispatchers.IO){
        repository.deleteCompletedTasks()
    }

    fun completeTodo(id: Int, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO){
        repository.completeTodo(id, isChecked)
    }

    fun getTasksByCategory(categoryName: String) = viewModelScope.launch(Dispatchers.IO){
        repository.getTasksByCategory(categoryName)
    }

    fun insertCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        repository.insertCategory(Category(categoryName))
    }
}