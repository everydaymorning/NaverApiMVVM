package com.example.refactoringproject.ui.login

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.refactoringproject.ui.main.MainActivity
import com.example.refactoringproject.R
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.data.OAuthLoginState
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {
    private val mLoginManager = LoginManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        this.supportActionBar?.hide()
        init()
    }

    private fun init(){
        btn_naver_button.setOAuthLoginHandler(LoginManager)

    }
}
