package ro.twodoors.simplelist.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Todo::class, Category::class], version = 5, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao() : TodoDao
    abstract fun categoryDao() : CategoryDao

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