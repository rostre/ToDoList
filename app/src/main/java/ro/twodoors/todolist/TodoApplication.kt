package ro.twodoors.todolist

import android.app.Application
import ro.twodoors.todolist.model.TodoDatabase
import ro.twodoors.todolist.model.TodoRepository
import ro.twodoors.todolist.model.TodoRoomRepository

class TodoApplication : Application() {

    val todoRepository: TodoRepository
        get() = TodoRoomRepository(TodoDatabase.getDatabase(this.applicationContext).todoDao())
}