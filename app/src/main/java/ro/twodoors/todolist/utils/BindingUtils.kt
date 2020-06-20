@file:Suppress("DEPRECATION")

package ro.twodoors.todolist.utils

import android.graphics.Color
import android.view.View
import androidx.databinding.BindingAdapter
import ro.twodoors.todolist.R
import java.text.SimpleDateFormat
import java.util.*

@BindingAdapter("android:isGone")
fun View.bindIsGone(isGone: Boolean) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

//@BindingAdapter("android:applyPriority")
//fun View.applyPriorityColor(priority: Priority) {
//    when(priority){
//        Priority.HIGH -> setBackgroundColor(resources.getColor(R.color.priority_high))
//        Priority.MEDIUM -> setBackgroundColor(resources.getColor(R.color.priority_medium))
//        Priority.LOW -> setBackgroundColor(resources.getColor(R.color.priority_low))
//        Priority.DEFAULT -> setBackgroundColor(Color.WHITE)
//    }
//}

//fun intToPriority(value: Int): Priority? {
//    var priority: Priority? = null
//    when(value){
//        0 -> priority = Priority.DEFAULT
//        1 -> priority = Priority.LOW
//        2 -> priority = Priority.MEDIUM
//        3 -> priority = Priority.HIGH
//    }
//    return priority
//}

fun convertLongToString(timeInMillis: Long) : String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    val sdf = SimpleDateFormat("MMMM dd, YYYY")
    return sdf.format(calendar.time)
}

//
//enum class Priority {
//    DEFAULT,
//    LOW,
//    MEDIUM,
//    HIGH
//}