package ro.twodoors.todolist

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ro.twodoors.todolist.viewmodel.ViewModelFactory

fun <T : ViewModel> Activity.obtainViewModel(viewModelClass: Class<T>): T {
    val todoRepository = (this.application as TodoApplication).todoRepository
    return ViewModelProvider(this as ViewModelStoreOwner, ViewModelFactory(todoRepository)).get(
        viewModelClass)
}