package ro.twodoors.simplelist.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import ro.twodoors.simplelist.data.*
import java.util.concurrent.CopyOnWriteArrayList
import javax.inject.Singleton

@InstallIn(ApplicationComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context) : TodoDatabase {
        return Room.databaseBuilder(
            appContext,
            TodoDatabase::class.java,
            "todo_database")
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    fun provideTodoDao(database: TodoDatabase) : TodoDao {
        return database.todoDao()
    }

    @Provides
    fun provideCategoryDao(database: TodoDatabase) : CategoryDao {
        return database.categoryDao()
    }

    @Provides
    fun provideCategoryRepository(database: TodoDatabase) : CategoryRepository{
        return CategoryRepositoryImpl(database.categoryDao())
    }

    @Provides
    fun provideToDoRepository(database: TodoDatabase) :TodoRepository {
        return TodoRepositoryImpl(database.todoDao())
    }
}