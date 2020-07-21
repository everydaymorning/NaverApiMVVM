package com.example.refactoringproject.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringproject.MyApplication

import com.example.refactoringproject.R
import com.example.refactoringproject.adapter.ShoppingAdapter
import com.example.refactoringproject.constant.ApiConstant
import com.example.refactoringproject.data.Shopping
import com.example.refactoringproject.data.ShoppingItem
import com.example.refactoringproject.network.RetrofitNetwork
import com.example.refactoringproject.ui.detail.DetailActivity
import kotlinx.android.synthetic.main.fragment_shopping_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ShoppingListFragment : Fragment() {
    private val mShoppingList: ArrayList<Shopping> = ArrayList()
    private val mContext = MyApplication.applicationContext()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val title = arguments?.getString("title")
        Log.d("frag_title", title.toString())
        val retrofit = RetrofitNetwork.create().getShoppingItem(ApiConstant.CLIENT_ID, ApiConstant.CLIENT_SECRET, title!!)
        retrofit.enqueue(
            object : Callback<ShoppingItem>{
                override fun onResponse(
                    call: Call<ShoppingItem>,
                    response: Response<ShoppingItem>
                ) {
                    if(response.isSuccessful){
                        val body = response.body()
                        for(i in body?.items!!){
                            mShoppingList.add(Shopping(i.title, i.link, i.image, i.lprice, i.mallName, i.maker, i.brand))
                        }
                        rv_fragment.apply{
                            layoutManager = LinearLayoutManager(mContext)
                            adapter = ShoppingAdapter(mShoppingList) { shopping ->
                                val intent = Intent(mContext, DetailActivity::class.java)
                                intent.putExtra("shoppingItem", shopping)
                                startActivity(intent)
                            }
                        }
                    }else{
                        Log.d("retro error", response.errorBody().toString())
                    }
                }

                override fun onFailure(call: Call<ShoppingItem>, t: Throwable) {

                }
            }
        )
    }
}
