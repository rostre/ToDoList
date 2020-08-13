package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.model.Todo
import ro.twodoors.simplelist.model.TodoRepository

class CategoryViewModel(private val repository: TodoRepository) : ViewModel() {

    fun completeTodo(id: Int, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO){
        repository.completeTodo(id, isChecked)
    }

    val allTodos : LiveData<List<Todo>> = repository.getAllTodos()

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(todo)
    }

    fun deleteCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        repository.deleteCategory(categoryName)
    }

}