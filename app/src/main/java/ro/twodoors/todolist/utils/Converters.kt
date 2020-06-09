package ro.twodoors.todolist.utils

import androidx.room.TypeConverter

class Converters {

    @TypeConverter
    fun intToPriority(value: Int): Priority? {
        var priority: Priority? = null
            when(value){
                0 -> priority = Priority.DEFAULT
                1 -> priority = Priority.LOW
                2 -> priority = Priority.MEDIUM
                3 -> priority = Priority.HIGH
            }
        return priority
    }

    @TypeConverter
    fun priorityToInt(priority: Priority): Int {
        return when(priority){
            Priority.DEFAULT -> 0
            Priority.LOW -> 1
            Priority.MEDIUM -> 2
            Priority.HIGH -> 3
        }
    }
}