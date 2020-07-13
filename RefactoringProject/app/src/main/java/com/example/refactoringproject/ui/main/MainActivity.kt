package com.example.refactoringproject.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.refactoringproject.MyApplication
import com.example.refactoringproject.R
import com.example.refactoringproject.data.UserProfile
import com.example.refactoringproject.database.UserLog
import com.example.refactoringproject.database.UserLogDAO
import com.example.refactoringproject.database.UserLogDB
import com.example.refactoringproject.network.RetrofitNetwork
import com.example.refactoringproject.ui.fragment.ShoppingListFragment
import com.example.refactoringproject.ui.login.LoginManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val mLoginManager = LoginManager
    private lateinit var mToken: String
    private val userLogDao by lazy {UserLogDB.getInstance(this).getUserLogDAO()}
    private lateinit var id: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        val title = edit_search.text
        mToken = mLoginManager.getAccessToken()

        Log.d("Token", mToken)
        val retrofit = RetrofitNetwork.create().getProfileData("Bearer $mToken")
        retrofit.enqueue(
            object: Callback<UserProfile> {
                override fun onResponse(
                    call: Call<UserProfile>,
                    response: Response<UserProfile>
                ) {
                    if(response.isSuccessful){
                        id = response.body()?.response?.id.toString()
                        Log.d("userId", id)
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {

                }
            }
        )

        btn_search.setOnClickListener{
            supportFragmentManager.beginTransaction().replace(R.id.frame_main,
                ShoppingListFragment().apply{
                arguments = Bundle().apply{
                    putString("title", title.toString())
                }
            }).commit()

            CoroutineScope(Dispatchers.IO).launch {
                userLogDao.insert(UserLog(userId = id,
                    log = title.toString()))
            }
        }
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
                true
            }
            R.id.menu_withdrawal -> {
                mLoginManager.logoutAndDeleteToken()
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
