package ro.twodoors.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.todolist.model.Todo
import ro.twodoors.todolist.model.TodoRepository

class AddViewModel(private val repository: TodoRepository) : ViewModel() {

    val allCategories = repository.getCategoryNames()

    val todo = Todo(
        title = "",
        description = "",
        categoryName = "",
        dueDate = null,
        completed = false,
        createdOn = null
    )

    fun save(): String? {
        if (todo.title == "") return "Title is required"

        todo.createdOn = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(todo)
        }
//        viewModelScope.launch(Dispatchers.IO){
//            repository.updateCategory(todo.category)
//        }
        return null
    }

}