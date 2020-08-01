package ro.twodoors.todolist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ro.twodoors.todolist.model.Todo
import ro.twodoors.todolist.model.TodoRepository

class CategoryViewModel(private val repository: TodoRepository) : ViewModel() {

    fun completeTodo(id: Int, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO){
        repository.completeTodo(id, isChecked)
    }

    val allTodos : LiveData<List<Todo>> = repository.getAllTodos()

    fun delete(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(todo)
    }

    fun deleteCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        repository.deleteCategory(categoryName)
    }

}