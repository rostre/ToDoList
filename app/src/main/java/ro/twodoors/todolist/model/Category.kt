package ro.twodoors.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
data class Category(
    @PrimaryKey @ColumnInfo(name = "category") var categoryName: String,
    var relations: Int = 0)
