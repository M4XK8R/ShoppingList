package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShopListRepositoryImpl

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private var count = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) { listOfShopItems ->
            Log.d("TAG", listOfShopItems.toString())
            if (count == 0) {
                val shopItemFirst = listOfShopItems[0]
                viewModel.changeIsEnableState(shopItemFirst)
                count++
            }
        }
    }
}