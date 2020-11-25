package ro.twodoors.simplelist.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DD_MMM_YYY = "dd MMM, YYYY"
private const val H_MM_AMPM = "h:mm a"
private val dateFormat = SimpleDateFormat(DD_MMM_YYY)
private val timeFormat = SimpleDateFormat(H_MM_AMPM)

fun formatDate(timestamp : Long) : String {
    return dateFormat.format(Date(timestamp))
}

fun formatTime(timestamp : Long) : String {
    return timeFormat.format(Date(timestamp))
}


