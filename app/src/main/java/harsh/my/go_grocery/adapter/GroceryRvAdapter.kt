package harsh.my.go_grocery.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import harsh.my.go_grocery.database.GroceryItems
import harsh.my.go_grocery.databinding.GroceryRecyclerviewItemsBinding

class GroceryRvAdapter(
    var list: List<GroceryItems>,
    val groceryItemClickInterface: GroceryItemClickInterface
) : RecyclerView.Adapter<GroceryRvAdapter.GroceryViewHolder>() {


    inner class GroceryViewHolder(var binding: GroceryRecyclerviewItemsBinding) :
        RecyclerView.ViewHolder(binding.root) {


    }

    interface GroceryItemClickInterface {
        fun onItemClick(groceryItems: GroceryItems) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroceryViewHolder {

        return GroceryViewHolder(
            GroceryRecyclerviewItemsBinding.inflate(
                LayoutInflater.from
                    (parent.context),
                parent,
                false
            )
        )


    }

    override fun onBindViewHolder(holder: GroceryViewHolder, position: Int) {

        holder.binding.itemName.text = list[position].itemName
        holder.binding.itemQuantity.text = list[position].itemQuantity.toString()
        holder.binding.itemRate.text = "₹ " + list[position].itemPrice.toString()

        val itemTotal: Float = list[position].itemPrice * list[position].itemQuantity

        holder.binding.totalAmount.text = "₹ " + itemTotal.toString()

        holder.binding.delete.setOnClickListener {
            groceryItemClickInterface.onItemClick(list[position])

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}