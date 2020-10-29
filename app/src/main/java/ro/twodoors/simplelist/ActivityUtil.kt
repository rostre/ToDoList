package ro.twodoors.simplelist

import android.app.Activity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import ro.twodoors.simplelist.viewmodel.ViewModelFactory

fun <T : ViewModel> Activity.obtainViewModel(viewModelClass: Class<T>): T {
    val todoRepository = (this.application as TodoApplication).todoRepository
    val categoryRepository = (this.application as TodoApplication).categoryRepository
    return ViewModelProvider(this as ViewModelStoreOwner, ViewModelFactory(todoRepository, categoryRepository)).get(
        viewModelClass)
}