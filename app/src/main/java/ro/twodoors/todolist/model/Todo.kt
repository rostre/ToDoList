package ro.twodoors.todolist.model

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "todo_table",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["name"],
        childColumns = ["categoryName"],
       onDelete = CASCADE,
       onUpdate = CASCADE)])
 data class Todo(
    @PrimaryKey(autoGenerate = false) var id: Int? = null,
    var title: String,
    var description: String,
    var categoryName: String,
    var dueDate: Long?,
    var completed: Boolean = false,
    var createdOn: Long?)

