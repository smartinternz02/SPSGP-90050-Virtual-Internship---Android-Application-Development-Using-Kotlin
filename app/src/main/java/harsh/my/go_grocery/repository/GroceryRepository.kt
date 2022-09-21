package harsh.my.go_grocery.repository

import harsh.my.go_grocery.database.GroceryDatabase
import harsh.my.go_grocery.database.GroceryItems

class GroceryRepository(private val db: GroceryDatabase) {

    suspend fun insert(items: GroceryItems) = db.getGroceryDao().insert(items)
    suspend fun delete(items: GroceryItems) = db.getGroceryDao().delete(items)

    fun getAllItems() = db.getGroceryDao().getAllGroceryItems()

}