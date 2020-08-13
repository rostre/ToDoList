package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.model.Todo
import ro.twodoors.simplelist.model.TodoRepository

class AddTodoViewModel(private val repository: TodoRepository) : ViewModel() {

    val allCategories = repository.getCategoryNames()

    val todo = Todo(
        title = "",
        description = "",
        categoryName = "",
        dueDate = null,
        completed = false,
        dueTime = null
    )

    fun save(): String? {
        if (todo.title == "") return "Title is required"
        if(todo.categoryName.isEmpty())
            return "Category is required"

        todo.dueTime = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(todo)
        }
        return null
    }

}