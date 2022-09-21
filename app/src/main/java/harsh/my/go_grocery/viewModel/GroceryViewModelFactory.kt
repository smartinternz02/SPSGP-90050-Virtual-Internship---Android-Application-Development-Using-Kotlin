package harsh.my.go_grocery.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

import harsh.my.go_grocery.repository.GroceryRepository


class GroceryViewModelFactory(private val repository: GroceryRepository) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return GroceryViewModel(repository) as T
    }
}