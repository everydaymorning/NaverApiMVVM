package com.example.refactoringproject

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler
import com.nhn.android.naverlogin.data.OAuthLoginState
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    val oAuthLoginModule = OAuthLogin.getInstance()
    val mContext = this
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        oAuthLoginModule.getRefreshToken(this)
        oAuthLoginModule.init(
            this,
            getString(R.string.CLIENT_ID),
            getString(R.string.CLIENT_SECRET),
            getString(R.string.CLIENT_NAME)
        )

        btn_naver_button.setOAuthLoginHandler(@SuppressLint("HandlerLeak")
        object: OAuthLoginHandler(){
            override fun run(success: Boolean){
                if(success){
                    val accessToken = oAuthLoginModule.getAccessToken(mContext)
                    val refreshToken = oAuthLoginModule.getRefreshToken(mContext)
                    val expiresAt = oAuthLoginModule.getExpiresAt(mContext)
                    val tokenType = oAuthLoginModule.getTokenType(mContext)
                    Log.i("TAG", "nhn Login Access Token : $accessToken")
                    Log.i("TAG", "nhn Login refresh Token : $refreshToken")
                    Log.i("TAG", "nhn Login expiresAt : $expiresAt")
                    Log.i("TAG", "nhn Login Token Type : $tokenType")
                    Log.i("TAG", "nhn Login Module State : " + oAuthLoginModule.getState(mContext).toString())
                    val intent = Intent(mContext, MainActivity::class.java)
                    startActivity(intent)

                } else{
                    val errorCode = oAuthLoginModule.getLastErrorCode(mContext)
                    val errorDesc = oAuthLoginModule.getLastErrorDesc(mContext)
                    Toast.makeText(mContext, "error code: $errorCode  errorDesc: $errorDesc", Toast.LENGTH_LONG).show()
                }
            }
        })

    }




    private fun naverSignIn(){
        if(oAuthLoginModule.getState(this) == OAuthLoginState.OK){
            CoroutineScope(Dispatchers.IO).launch {
                val url = "https://openapi.naver.com/v1/nid/me"
                val at = oAuthLoginModule.getAccessToken(mContext)
                val result = oAuthLoginModule.requestApi(mContext, at, url)

            }
        }
    }




}
