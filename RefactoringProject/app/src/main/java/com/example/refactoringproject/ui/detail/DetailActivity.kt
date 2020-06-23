package com.example.refactoringproject.ui.detail

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.refactoringproject.R
import com.example.refactoringproject.data.Shopping
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val shoppingItem = intent.getParcelableExtra<Shopping>("shoppingItem")
        txt_title_detail.text = "제품명: " + HtmlCompat.fromHtml(shoppingItem.title, HtmlCompat.FROM_HTML_MODE_LEGACY)
        txt_price_detail.text = "최저가: " + shoppingItem.lprice.toString() + "원"
        Glide.with(applicationContext).load(shoppingItem.image)
            .apply(RequestOptions())
            .apply(RequestOptions().centerCrop())
            .into(img_shopping_detail)

        btn_link.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(shoppingItem.link))
            startActivity(intent)
        }
    }

}
