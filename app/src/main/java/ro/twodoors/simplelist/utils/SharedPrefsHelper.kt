package ro.twodoors.simplelist.utils

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import ro.twodoors.simplelist.utils.Constants.Companion.CATEGORY_KEY

class SharedPrefsHelper {

    companion object{
        private fun preferences(context: Context) : SharedPreferences =
            context.getSharedPreferences(CATEGORY_KEY, Context.MODE_PRIVATE)

        fun saveColorToPref(context: Context, key: String, value: Int){
            preferences(context).edit()
                .putInt(key, value)
                .apply()
        }

        fun getColorFromPref(context: Context, key: String) : Int  =
            preferences(context).getInt(key, Color.BLACK)

        fun removeColorFromPref(context: Context, key: String){
            preferences(context).edit()
                .remove(key)
                .apply()
        }
    }
}