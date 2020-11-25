@file:Suppress("DEPRECATION")

package ro.twodoors.simplelist.utils

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:isGone")
fun View.bindIsGone(isGone: Boolean) {
    visibility = if (isGone) {
        View.GONE
    } else {
        View.VISIBLE
    }
}

@BindingAdapter("android:displayDateTime")
fun TextView.displayDateTime(time: Long?) {
    this.bindIsGone(time == null)
}