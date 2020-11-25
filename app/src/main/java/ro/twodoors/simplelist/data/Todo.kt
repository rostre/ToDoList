package ro.twodoors.simplelist.data

import androidx.room.*
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "todo_table",
    foreignKeys = [ForeignKey(
        entity = Category::class,
        parentColumns = ["category_name"],
        childColumns = ["task_category"],
        onDelete = CASCADE,
        onUpdate = CASCADE)],
        indices = [Index(value = ["task_category"])])
 data class Todo(
    @ColumnInfo(name = "task_id") @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "task_title") var title: String,
    @ColumnInfo(name = "task_description") var description: String? = null,
    @ColumnInfo(name = "task_category") var category: String,
    @ColumnInfo(name = "task_due_date") var dueDate: Long? = null,
    @ColumnInfo(name = "task_is_completed") var completed: Boolean = false,
    @ColumnInfo(name = "task_due_time") var dueTime: Long? = null)

