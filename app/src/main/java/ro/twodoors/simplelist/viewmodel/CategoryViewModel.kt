package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.model.CategoryRepository
import ro.twodoors.simplelist.model.Todo
import ro.twodoors.simplelist.model.TodoRepository

class CategoryViewModel(private val repository: TodoRepository, private val categoryRepo: CategoryRepository) : ViewModel() {

    val allTodos : LiveData<List<Todo>> = repository.getAllTodos()

    fun completeTodo(id: Int, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO){
        repository.completeTodo(id, isChecked)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(todo)
    }

    fun deleteCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        categoryRepo.deleteCategory(categoryName)
    }

}