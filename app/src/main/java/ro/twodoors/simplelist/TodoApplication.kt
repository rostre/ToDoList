package ro.twodoors.simplelist

import android.app.Application
import ro.twodoors.simplelist.model.*

class TodoApplication : Application() {

    val todoRepository: TodoRepository
        get() = TodoRepositoryImpl(TodoDatabase.getDatabase(this.applicationContext).todoDao())

    val categoryRepository: CategoryRepository
        get() = CategoryRepositoryImpl(TodoDatabase.getDatabase(this.applicationContext).categoryDao())
}