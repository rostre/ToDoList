package ro.twodoors.simplelist.view.adapter

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ro.twodoors.simplelist.R
import ro.twodoors.simplelist.utils.SharedPrefsHelper

class SpinnerCategoryAdapter(context: Context, categoriesList: List<String>)
    : ArrayAdapter<String>(context, 0, categoriesList) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (position == 0){
            view = layoutInflater.inflate(R.layout.category_item_header, parent, false)
        }
        else {
            view = layoutInflater.inflate(R.layout.category_item, parent, false)
            getItem(position)?.let { category ->
                setupCategoryItem(view, category)
            }
        }
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (position == 0){
            view = layoutInflater.inflate(R.layout.category_item_header, parent, false)
            view.setOnClickListener {
                val root = parent.rootView
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_BACK))
                root.dispatchKeyEvent(KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_BACK))
            }
        } else {
            view = layoutInflater.inflate(R.layout.category_item, parent, false)
            getItem(position)?.let { category ->
                setupCategoryItem(view, category)
            }
        }
        return view
    }

    override fun getItem(position: Int): String? {
        if (position == 0) {
            return null
        }
        return super.getItem(position - 1)
    }

    override fun getCount() = super.getCount() + 1

    override fun isEnabled(position: Int) = position != 0

    private fun setupCategoryItem(view: View, category: String) {
        view.findViewById<TextView>(R.id.tvCategoryItemName)
            .text = category

        view.findViewById<TextView>(R.id.tvCategoryItemColor)
            .setBackgroundColor(SharedPrefsHelper.getColorFromPref(context, category))
    }
}