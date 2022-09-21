package harsh.my.go_grocery


import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import harsh.my.go_grocery.adapter.GroceryRvAdapter
import harsh.my.go_grocery.database.GroceryDatabase
import harsh.my.go_grocery.database.GroceryItems
import harsh.my.go_grocery.databinding.ActivityMainBinding
import harsh.my.go_grocery.registration.RegistrationActivity
import harsh.my.go_grocery.repository.GroceryRepository
import harsh.my.go_grocery.viewModel.GroceryViewModel
import harsh.my.go_grocery.viewModel.GroceryViewModelFactory


class MainActivity : AppCompatActivity(), GroceryRvAdapter.GroceryItemClickInterface {

    private lateinit var binding: ActivityMainBinding
    private lateinit var list: List<GroceryItems>
    private lateinit var groceryRvAdapter: GroceryRvAdapter
    lateinit var groceryViewModel: GroceryViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        list = ArrayList<GroceryItems>()

        groceryRvAdapter = GroceryRvAdapter(list, this)

        binding.rvItems.layoutManager = LinearLayoutManager(this)

        binding.rvItems.adapter = groceryRvAdapter

        val groceryRepository = GroceryRepository(GroceryDatabase(this))

        val factory = GroceryViewModelFactory(groceryRepository)

        groceryViewModel = ViewModelProvider(this, factory)[GroceryViewModel::class.java]

        groceryViewModel.getAllGroceryItems().observe(this, Observer {
            groceryRvAdapter.list = it
            groceryRvAdapter.notifyDataSetChanged()
        })

        binding.fabAdd.setOnClickListener {
            openDialog()
        }
//For logout Button:
        binding.btnLogout.setOnClickListener {

            val fAuth = FirebaseAuth.getInstance()
            fAuth.signOut()
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Logout Successful", Toast.LENGTH_SHORT).show()

        }

    }

    private fun openDialog() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.grocery_dialog)

        val cancelBtn = dialog.findViewById<Button>(R.id.btn_cancel)
        val addBtn = dialog.findViewById<Button>(R.id.btn_add)


        val itemEdt = dialog.findViewById<EditText>(R.id.item_na)
        val itemPrice = dialog.findViewById<EditText>(R.id.item_pri)
        val itemQuantity = dialog.findViewById<EditText>(R.id.item_quant)

        cancelBtn.setOnClickListener {
            dialog.dismiss()
        }

        addBtn.setOnClickListener {
            if (validateInput(itemEdt, itemQuantity, itemPrice)) {
                addItemToDB(
                    itemEdt.text.toString(),
                    itemQuantity.text.toString(),
                    itemPrice.text.toString()
                )
                dialog.dismiss()
            }
        }

        dialog.show()

    }

    private fun validateInput(item: TextView, quantity: TextView, price: TextView): Boolean {
        if (item.text.isEmpty()) {
            item.error = "Name is empty."
            return false
        }
        if (quantity.text.isEmpty()) {
            quantity.error = "Quantity is empty."
            return false
        }
        if (price.text.isEmpty()) {
            price.error = "Price is empty."
            return false
        }
        return true
    }

    private fun addItemToDB(itemName: String, quantity: String, price: String) {
        val items = GroceryItems(itemName, quantity.toInt(), price.toFloat())
        groceryViewModel.insert(items)
        Toast.makeText(this, "Item Added!", Toast.LENGTH_SHORT).show()
        groceryRvAdapter.notifyDataSetChanged()
    }


    override fun onItemClick(groceryItems: GroceryItems) {
        groceryViewModel.delete(groceryItems)
        groceryRvAdapter.notifyDataSetChanged()
        Snackbar.make(binding.root, "Item Deleted !!", Snackbar.LENGTH_SHORT).show()

    }
}