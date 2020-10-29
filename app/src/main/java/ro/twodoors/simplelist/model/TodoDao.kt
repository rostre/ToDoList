package ro.twodoors.simplelist.model

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("DELETE FROM todo_table")
    suspend fun deleteAll()

    @Query("DELETE FROM todo_table WHERE task_is_completed = 1")
    suspend fun deleteCompletedTasks()

    @Query("SELECT * FROM todo_table ORDER BY task_due_date DESC, task_title")
    fun getAll() : LiveData<List<Todo>>

    @Query("SELECT COUNT(*) FROM todo_table")
    fun getAllTodosCount() : LiveData<Int>

    @Query("SELECT COUNT(*) FROM todo_table WHERE task_is_completed = 1")
    fun getTodosCompletedCount() : LiveData<Int>

    @Query("UPDATE todo_table set task_is_completed = :isChecked WHERE task_id = :id")
    suspend fun completeTodo(id: Int, isChecked: Boolean)
}