package com.example.refactoringproject.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.refactoringproject.R
import com.example.refactoringproject.ui.login.LoginManager

class MainActivity : AppCompatActivity() {
    private val mLoginManager = LoginManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when(item?.itemId){
            R.id.menu_logout -> {
                mLoginManager.logout()
                finish()
                mLoginManager.goLoginActivity()
                true
            }
            R.id.menu_withdrawal -> {
                mLoginManager.logoutAndDeleteToken()
                finish()
                mLoginManager.goLoginActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


}
