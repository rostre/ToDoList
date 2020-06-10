package ro.twodoors.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.todolist.model.Category
import ro.twodoors.todolist.model.Todo
import ro.twodoors.todolist.model.TodoRepository

class AddViewModel(private val repository: TodoRepository) : ViewModel() {

    val todo = Todo(
        title = "",
        description = "",
        category = Category(""),
        priority = null,
        completed = false,
        createdOn = null
    )

    fun save(): String? {
        if (todo.title == "") return "Title is required"

        todo.createdOn = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(todo)
        }
        return null
    }
}