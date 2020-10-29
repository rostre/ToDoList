package ro.twodoors.simplelist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ro.twodoors.simplelist.model.CategoryRepository
import ro.twodoors.simplelist.model.TodoRepository

@Suppress("UNCHECKED_CAST")
class ViewModelFactory constructor(private val todoRepository: TodoRepository, private val categoryRepository: CategoryRepository) :
    ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        with(modelClass) {
            when {
                isAssignableFrom(TodoViewModel::class.java) ->
                    TodoViewModel(todoRepository, categoryRepository)
                isAssignableFrom(AddTodoViewModel::class.java) ->
                    AddTodoViewModel(todoRepository, categoryRepository)
                isAssignableFrom(CategoryViewModel::class.java) ->
                    CategoryViewModel(todoRepository, categoryRepository)
                isAssignableFrom(AddCategoryViewModel::class.java) ->
                    AddCategoryViewModel(categoryRepository)
                else ->
                    throw IllegalArgumentException("ViewModel class (${modelClass.name}) is not mapped")
            }
        } as T
}