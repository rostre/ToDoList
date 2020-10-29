package ro.twodoors.simplelist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category_table")
 data class Category(
    @ColumnInfo(name = "category_name") @PrimaryKey var name: String)

