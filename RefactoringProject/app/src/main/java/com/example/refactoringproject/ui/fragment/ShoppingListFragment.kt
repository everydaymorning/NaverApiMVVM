package com.example.refactoringproject.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.refactoringproject.MyApplication

import com.example.refactoringproject.R
import com.example.refactoringproject.adapter.ShoppingAdapter
import com.example.refactoringproject.data.Shopping
import kotlinx.android.synthetic.main.fragment_shopping_list.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ShoppingListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoppingListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    val testData: ArrayList<Shopping> = ArrayList()
    private lateinit var mShoppingAdapter: ShoppingAdapter
    private val mContext = MyApplication.applicationContext()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))
        testData.add(Shopping("title", "test2", "test3", 1, "mallName", "test5", "test6"))

        return inflater.inflate(R.layout.fragment_shopping_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        rv_fragment.apply{
            layoutManager = LinearLayoutManager(mContext)
            adapter = ShoppingAdapter(testData)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ShoppingListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShoppingListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
