package ro.twodoors.simplelist.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Todo::class, Category::class], version = 5, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {

    abstract fun todoDao() : TodoDao
    abstract fun categoryDao() : CategoryDao

}