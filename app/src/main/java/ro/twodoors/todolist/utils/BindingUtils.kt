@file:Suppress("DEPRECATION")

package ro.twodoors.todolist.utils

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.databinding.BindingAdapter
import ro.twodoors.todolist.view.activity.MainActivity
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

//fun getColorFromSharedPref(activity: Activity, key: String) : Int {
//    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return 0
//    return sharedPref.getInt(key, 0)
//}

//@BindingAdapter("android:setBackgroundColor")
//fun View.getColorFromSharedPref(activity: Activity, key: String) {
//    val sharedPref = activity.getPreferences(Context.MODE_PRIVATE) ?: return
//    setBackgroundColor(sharedPref.getInt(key, 0))
//}

fun convertLongToString(timeInMillis: Long) : String{
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = timeInMillis
    val sdf = SimpleDateFormat("MMMM dd, YYYY")
    return sdf.format(calendar.time)
}