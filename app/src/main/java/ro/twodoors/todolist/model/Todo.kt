package ro.twodoors.todolist.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import ro.twodoors.todolist.utils.Priority

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    var title: String,
    var description: String,
    var category: Category,
    var priority: Priority?,
    var completed: Boolean = false,
    var createdOn: Long?) {

}

