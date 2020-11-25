package ro.twodoors.simplelist.ui.categorydetail

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.data.CategoryRepository
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.data.TodoRepository

class CategoryDetailViewModel @ViewModelInject constructor(
    private val todoRepository: TodoRepository,
    private val categoryRepository: CategoryRepository) : ViewModel() {

    val allTodos : LiveData<List<Todo>> = todoRepository.getAllTodos()

    fun completeTodo(id: Int, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO){
        todoRepository.completeTodo(id, isChecked)
    }

    fun deleteTodo(todo: Todo) = viewModelScope.launch(Dispatchers.IO){
        todoRepository.delete(todo)
    }

    fun deleteCategory(categoryName: String) = viewModelScope.launch (Dispatchers.IO){
        categoryRepository.deleteCategory(categoryName)
    }

}