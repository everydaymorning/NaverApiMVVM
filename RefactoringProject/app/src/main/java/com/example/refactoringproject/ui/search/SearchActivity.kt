package com.example.refactoringproject.ui.search

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.example.refactoringproject.R
import kotlinx.android.synthetic.main.activity_search.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)
        val recentLog = intent.getStringArrayListExtra("recentLog")
        auto_edit.setAdapter(ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, recentLog))

        btn_search2.setOnClickListener{
            val intent = Intent()
            intent.putExtra("search", auto_edit.text.toString())
            setResult(Activity.RESULT_OK, intent)
            finish()
        }


    }


}
