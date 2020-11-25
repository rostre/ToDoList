package ro.twodoors.simplelist.utils

import android.content.Context
import android.view.View

fun View.getStringResource(id : Int, arg: Int? = null) : String {
    return when (arg) {
        null -> this.context.resources.getString(id)
        else -> this.context.resources.getString(id, arg)
    }
}