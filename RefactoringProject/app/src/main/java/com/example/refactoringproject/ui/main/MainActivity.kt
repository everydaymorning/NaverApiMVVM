package com.example.refactoringproject.ui.main

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.example.refactoringproject.MyApplication
import com.example.refactoringproject.R
import com.example.refactoringproject.data.UserProfile
import com.example.refactoringproject.database.UserLog
import com.example.refactoringproject.database.UserLogDB
import com.example.refactoringproject.network.RetrofitNetwork
import com.example.refactoringproject.ui.fragment.ShoppingListFragment
import com.example.refactoringproject.ui.login.LoginManager
import com.example.refactoringproject.ui.search.SearchActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val mLoginManager = LoginManager
    private val mToken: String by lazy {mLoginManager.getAccessToken()}
    private val userLogDao by lazy {UserLogDB.getInstance(this).getUserLogDAO()}
    private lateinit var userId: String
    init{

        val retrofit = RetrofitNetwork.create().getProfileData("Bearer $mToken")
        retrofit.enqueue(
            object: Callback<UserProfile> {
                override fun onResponse(
                    call: Call<UserProfile>,
                    response: Response<UserProfile>
                ) {
                    if(response.isSuccessful){
                        userId = response.body()?.response?.id.toString()
                        Log.d("userId", userId)

                        btn_search.setOnClickListener{
                            Log.d("title", edit_search.text.toString())
                            val title = edit_search.text.toString()
                            var isDuplicated = false
                            supportFragmentManager.beginTransaction().replace(R.id.frame_main,
                                ShoppingListFragment().apply{
                                    arguments = Bundle().apply{
                                        putString("title", title)
                                    }
                                }).commit()
                            CoroutineScope(Dispatchers.IO).launch {
                                val history = userLogDao.getAll(userId)
                                Log.d("history", history.toString())
                                if(history.contains(title)){
                                    isDuplicated = true
                                }
                                if(!isDuplicated) {
                                    userLogDao.insert(
                                        UserLog(
                                            userId = userId,
                                            log = title
                                        )
                                    )
                                }
                            }
                        }
                    }
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {

                }
            }
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        Log.d("Token", mToken)

        edit_search.setOnClickListener{
            CoroutineScope(Dispatchers.IO).launch {
                val recentLog = userLogDao.getRecentLog(userId)
                val intent = Intent(MyApplication.applicationContext(), SearchActivity::class.java)
                intent.putStringArrayListExtra("recentLog", recentLog as ArrayList<String>)
                startActivityForResult(intent, 100)
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
                CoroutineScope(Dispatchers.IO).launch {
                    userLogDao.deleteLog(userId)
                }
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                100 -> {
                    edit_search.setText(data?.getStringExtra("search").toString())
                }
                else -> {
                    edit_search.setText("")
                }
            }
        }
    }

}
