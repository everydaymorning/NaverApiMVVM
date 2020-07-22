package com.example.refactoringproject.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ArrayAdapter
import com.example.refactoringproject.MyApplication
import com.example.refactoringproject.R
import com.example.refactoringproject.data.UserProfile
import com.example.refactoringproject.database.UserLog
import com.example.refactoringproject.database.UserLogDB
import com.example.refactoringproject.network.RetrofitNetwork
import com.example.refactoringproject.ui.fragment.ShoppingListFragment
import com.example.refactoringproject.ui.login.LoginManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val mLoginManager = LoginManager
    private lateinit var mToken: String
    private val userLogDao by lazy {UserLogDB.getInstance(this).getUserLogDAO()}
    private lateinit var getRecentLog: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
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
                        userId = response.body()?.response?.id.toString()
                        CoroutineScope(Dispatchers.IO).launch {
                            getRecentLog = userLogDao.getRecentLog(userId!!)
                            Log.d("asdfa", getRecentLog.toString())
                        }
                        Log.d("userId", userId)
                        edit_search.setAdapter(ArrayAdapter(MyApplication.applicationContext(), android.R.layout.simple_dropdown_item_1line, getRecentLog))

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
                                val history = userLogDao.getAll(userId!!)
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
                    userLogDao.deleteLog(userId!!)
                }
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object{
        var userId: String?= null
    }


}
