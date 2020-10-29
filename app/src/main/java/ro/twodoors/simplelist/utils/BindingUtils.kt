@file:Suppress("DEPRECATION")

package ro.twodoors.simplelist.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentActivity
import ro.twodoors.simplelist.view.fragment.AddCategoryFragment
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

fun showAddCategoryFragment(activity: Activity) {
    val bottomSheetFragment = AddCategoryFragment(activity)
    bottomSheetFragment.show((activity as FragmentActivity).supportFragmentManager, bottomSheetFragment.tag)
}

fun convertLongToString(timeInMillis: Long) : String{
    val calendar = Calendar.getInstance()
    val currentYear = calendar.get(Calendar.YEAR)
    calendar.timeInMillis = timeInMillis
    val year = calendar.get(Calendar.YEAR)
    return when (currentYear) {
        year -> SimpleDateFormat("dd MMMM").format(calendar.time)
        else -> SimpleDateFormat("dd MMM, YYYY").format(calendar.time)
    }
}

