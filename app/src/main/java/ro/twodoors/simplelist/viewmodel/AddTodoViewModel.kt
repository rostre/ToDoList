package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ro.twodoors.simplelist.model.CategoryRepository
import ro.twodoors.simplelist.model.Todo
import ro.twodoors.simplelist.model.TodoRepository

class AddTodoViewModel(
    private val repository: TodoRepository,
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
        if (todo.title == "") return "Title is required"
        if(todo.category.isEmpty())
            return "Category is required"

        todo.dueTime = System.currentTimeMillis()
        viewModelScope.launch(Dispatchers.IO){
            repository.insert(todo)
        }
        return null
    }

}