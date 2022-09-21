package harsh.my.go_grocery.viewModel

import androidx.lifecycle.ViewModel

import harsh.my.go_grocery.database.GroceryItems
import harsh.my.go_grocery.repository.GroceryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class GroceryViewModel (private val repository: GroceryRepository):ViewModel()
{
    fun insert(item: GroceryItems) = GlobalScope.launch {
        repository.insert(item)
    }
    fun delete(item:GroceryItems) = GlobalScope.launch {
        repository.delete(item)
    }
    fun getAllGroceryItems() = repository.getAllItems()
}