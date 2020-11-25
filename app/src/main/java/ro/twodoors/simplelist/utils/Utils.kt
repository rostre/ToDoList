package ro.twodoors.simplelist.utils

import android.graphics.drawable.GradientDrawable

fun createGradientDrawable(radius : Float, color : Int) : GradientDrawable {
    return GradientDrawable()
        .apply {
            cornerRadius = radius
            setColor(color)
        }
}