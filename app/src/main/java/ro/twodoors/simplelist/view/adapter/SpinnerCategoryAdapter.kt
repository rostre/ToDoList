package ro.twodoors.simplelist.view.adapter

import android.content.Context
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ro.twodoors.simplelist.R

class SpinnerCategoryAdapter(context: Context, list: List<String>)
    : ArrayAdapter<String>(context, 0, list) {

    private val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View

        if (position == 0){
            view = layoutInflater.inflate(R.layout.category_item_header, parent, false)
        }
        else {
            view = layoutInflater.inflate(R.layout.category_item, parent, false)
            getItem(position)?.let { category ->
                setItemForCategory(view, category)
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
                setItemForCategory(view, category)
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

    private fun setItemForCategory(view: View, category: String) {
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        tvCountry.text = category

        val ivCountry = view.findViewById<TextView>(R.id.ivCountry)
        ivCountry.setBackgroundColor(getColorFromSharedPref(category))
    }

    fun getColorFromSharedPref(key: String) : Int {
        val sharedPref = context.getSharedPreferences("CATEGORY", Context.MODE_PRIVATE) ?: return 0
        return sharedPref.getInt(key, 0)
    }

}