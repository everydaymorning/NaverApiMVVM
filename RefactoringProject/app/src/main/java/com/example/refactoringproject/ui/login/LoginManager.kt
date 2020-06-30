package com.example.refactoringproject.ui.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.example.refactoringproject.MyApplication
import com.example.refactoringproject.constant.LoginConstant
import com.example.refactoringproject.ui.main.MainActivity
import com.nhn.android.naverlogin.OAuthLogin
import com.nhn.android.naverlogin.OAuthLoginHandler

object LoginManager: OAuthLoginHandler() {
    private val mContext = MyApplication.applicationContext()
    private var mActivity : Activity? = null
    private val mNaverLoginInstance = OAuthLogin.getInstance().apply{
        init(
            mContext,
            LoginConstant.CLIENT_ID,
            LoginConstant.CLIENT_SECRET,
            LoginConstant.CLIENT_NAME
        )
    }

    fun getRefreshToken() = mNaverLoginInstance?.getRefreshToken(mContext)

    fun getAccessToken() = mNaverLoginInstance.getAccessToken(mContext)
    fun startLoginActivity(activity: Activity){
        this.mActivity = activity
        mNaverLoginInstance?.startOauthLoginActivity(activity, this)
    }
    fun logout(){
        mNaverLoginInstance.logout(mContext)
        goLoginActivity()
    }

    fun logoutAndDeleteToken(){
        mNaverLoginInstance.logoutAndDeleteToken(mContext)
        goLoginActivity()
    }

    override fun run(success: Boolean) {
        if(success){
            goMainActivity()
            Log.d("login", "로그인 성공")

        }else{
            Log.e("login", "로그인 실패")
        }
    }

    fun goLoginActivity(){
        val intent = Intent(mContext, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NO_HISTORY
        mContext.startActivity(intent)
    }

    private fun goMainActivity(){
        val intent = Intent(mContext, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        mContext.startActivity(intent)
    }
}