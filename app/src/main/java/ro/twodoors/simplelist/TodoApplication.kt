package ro.twodoors.simplelist

import android.app.Application
import ro.twodoors.simplelist.model.TodoDatabase
import ro.twodoors.simplelist.model.TodoRepository
import ro.twodoors.simplelist.model.TodoRoomRepository

class TodoApplication : Application() {

    val todoRepository: TodoRepository
        get() = TodoRoomRepository(TodoDatabase.getDatabase(this.applicationContext).todoDao())
}