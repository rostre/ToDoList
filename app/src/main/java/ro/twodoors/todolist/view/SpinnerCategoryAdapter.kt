package ro.twodoors.todolist.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ro.twodoors.todolist.R
import ro.twodoors.todolist.model.Category

class SpinnerCategoryAdapter(
    context: Context, list: List<String>) : ArrayAdapter<String>(context, 0, list) {

    val layoutInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View
        if (convertView == null) {
            view = layoutInflater.inflate(R.layout.category_item, parent, false)
        } else {
            view = convertView
        }
        getItem(position)?.let { country ->
            setItemForCategory(view, country)
        }
        return view
    }
    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view: View = layoutInflater.inflate(R.layout.category_item, parent, false)
        getItem(position)?.let { category ->
            setItemForCategory(view, category)
        }
        return view
    }

    private fun setItemForCategory(view: View, category: String) {
        val tvCountry = view.findViewById<TextView>(R.id.tvCountry)
        tvCountry.text = category

    }

}
