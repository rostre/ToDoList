package ro.twodoors.simplelist.ui.addtodo

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.data.CategoryRepository
import ro.twodoors.simplelist.data.Todo
import ro.twodoors.simplelist.data.TodoRepository

class AddTodoViewModel @ViewModelInject constructor(
    private val todoRepository: TodoRepository,
    categoryRepo: CategoryRepository) : ViewModel() {

    val allCategories = categoryRepo.getCategoryNames()

    val todo = Todo(
        title = "",
        description = "",
        category = "",
        dueDate = null,
        completed = false,
        dueTime = null
    )

    fun save(): String? {
        if (todo.title == "")
            return  "Title is required"
        if(todo.category.isEmpty())
            return "Category is required"

        if(todo.dueDate == null && todo.dueTime != null)
            return "Select a date for the specified time"

        viewModelScope.launch(Dispatchers.IO){
            todoRepository.insert(todo)
        }
        return null
    }

}