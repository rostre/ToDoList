@file:Suppress("DEPRECATION")

package ro.twodoors.todolist.utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.withContext
import ro.twodoors.todolist.view.fragment.AddCategoryFragment
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

fun View.hideKeyboard(){
    val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}


fun showAddCategoryFragment(activity: Activity) {
    val bottomSheetFragment = AddCategoryFragment(activity)
    bottomSheetFragment.show((activity as FragmentActivity).supportFragmentManager, bottomSheetFragment.tag)
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
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.timeInMillis = timeInMillis
    val year = calendar.get(Calendar.YEAR)
    return when (currentYear) {
        year -> SimpleDateFormat("dd MMMM").format(calendar.time)
        else -> SimpleDateFormat("dd MMM, YYYY").format(calendar.time)
    }
    //return sdf.format(calendar.time)
}