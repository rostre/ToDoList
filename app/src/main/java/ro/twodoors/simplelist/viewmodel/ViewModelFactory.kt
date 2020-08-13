package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.twodoors.simplelist.model.TodoRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val todoRepository: TodoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(TodoViewModel::class.java) ->
                    TodoViewModel(todoRepository)
                isAssignableFrom(AddTodoViewModel::class.java) ->
                    AddTodoViewModel(todoRepository)
                isAssignableFrom(CategoryViewModel::class.java) ->
                    CategoryViewModel(todoRepository)
                isAssignableFrom(AddCategoryViewModel::class.java) ->
                    AddCategoryViewModel(todoRepository)
                else ->
                    throw IllegalArgumentException("ViewModel class (${modelClass.name}) is not mapped")
            }
        } as T
}