package ro.twodoors.simplelist.ui.main

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.data.Category
import ro.twodoors.simplelist.data.CategoryRepository
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.data.TodoRepository

class MainViewModel @ViewModelInject constructor(
    private val repository: TodoRepository,
    categoryRepo: CategoryRepository) : ViewModel()   {

    val allTodos : LiveData<List<Todo>> = repository.getAllTodos()

    val allCategories : LiveData<List<Category>> = categoryRepo.getAllCategories()

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