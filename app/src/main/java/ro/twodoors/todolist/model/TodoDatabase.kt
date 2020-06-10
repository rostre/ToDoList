package ro.twodoors.todolist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kotlinx.coroutines.CoroutineScope
import ro.twodoors.todolist.utils.Converters

@Database(entities = [Todo::class, Category::class], version = 5, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao() : TodoDao

    companion object{

        @Volatile
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context) : TodoDatabase{
            synchronized(this){
                var instance = INSTANCE

                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo_database")
                        .fallbackToDestructiveMigration()
                        .build()
                INSTANCE = instance
                }
                return instance
            }
        }
    }
}