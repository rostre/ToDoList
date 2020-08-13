package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.model.Category
import ro.twodoors.simplelist.model.Todo
import ro.twodoors.simplelist.model.TodoRepository

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

}