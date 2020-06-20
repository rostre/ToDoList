package ro.twodoors.todolist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.twodoors.todolist.model.TodoRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val todoRepository: TodoRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(TodoViewModel::class.java) ->
                    TodoViewModel(todoRepository)
                isAssignableFrom(AddViewModel::class.java) ->
                    AddViewModel(todoRepository)
                isAssignableFrom(CategoryViewModel::class.java) ->
                    CategoryViewModel(todoRepository)
                else ->
                    throw IllegalArgumentException("ViewModel class (${modelClass.name}) is not mapped")
            }
        } as T
}