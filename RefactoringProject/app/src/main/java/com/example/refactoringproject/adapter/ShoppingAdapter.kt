package com.example.refactoringproject.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.refactoringproject.MyApplication
import com.example.refactoringproject.R
import com.example.refactoringproject.data.Shopping
import kotlinx.android.synthetic.main.shopping_item.view.*

class ShoppingAdapter(var shoppingList: ArrayList<Shopping>, val itemClick: (Shopping) -> Unit) : RecyclerView.Adapter<ShoppingAdapter.ItemViewHolder>() {
    private val mallName = "판매처: "
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
            itemView.txt_title.text =
                HtmlCompat.fromHtml(shopping.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
            itemView.txt_mallName.text = mallName + shopping.mallName
            Glide.with(MyApplication.applicationContext()).load(shopping.image)
                .apply(RequestOptions().override(200, 200))
                .apply(RequestOptions().centerCrop())
                .into(itemView.img_shopping)

            itemView.setOnClickListener{
                itemClick(shopping)
            }
        }
    }
}