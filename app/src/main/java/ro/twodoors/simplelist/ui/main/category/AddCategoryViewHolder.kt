package ro.twodoors.simplelist.ui.main.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ro.twodoors.simplelist.databinding.CategoryAddItemBinding
import ro.twodoors.simplelist.ui.main.CategoryClickListener

class AddCategoryViewHolder(val binding: CategoryAddItemBinding) : RecyclerView.ViewHolder(binding.root){
    fun bind(clickListener: CategoryClickListener){
        binding.apply {
            this.clickListener = clickListener
            executePendingBindings()
        }
    }

    companion object{
        fun from(parent: ViewGroup) : RecyclerView.ViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = CategoryAddItemBinding.inflate(layoutInflater, parent, false)
            return AddCategoryViewHolder(
                binding
            )
        }
    }
}