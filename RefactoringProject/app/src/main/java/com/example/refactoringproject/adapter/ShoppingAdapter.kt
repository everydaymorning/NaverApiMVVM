package com.example.refactoringproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.refactoringproject.R
import com.example.refactoringproject.data.Shopping
import kotlinx.android.synthetic.main.shopping_item.view.*

class ShoppingAdapter(var shoppingList: ArrayList<Shopping>) : RecyclerView.Adapter<ShoppingAdapter.ItemViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ItemViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(R.layout.shopping_item, parent, false)
        return ItemViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bindItems(shoppingList[position])
    }

    override fun getItemCount(): Int = shoppingList.size

    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(shopping: Shopping) {
            itemView.txt_title.text = shopping.title
            itemView.txt_mallName.text = shopping.mallName

        }
    }
}