package com.example.shoppinglist.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var shopListAdapter: ShopListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpRecyclerView()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.shopList.observe(this) {
            shopListAdapter.submitList(it)
        }
    }


    private fun setUpRecyclerView() {
        val rvShopList = findViewById<RecyclerView>(R.id.rvShopList)
        with(rvShopList) {
            shopListAdapter = ShopListAdapter()
            adapter = shopListAdapter
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_ENABLED, ShopListAdapter.VH_MAX_POOL_SIZE
            )
            recycledViewPool.setMaxRecycledViews(
                ShopListAdapter.VIEW_TYPE_DISABLED, ShopListAdapter.VH_MAX_POOL_SIZE
            )
        }/*  shopListAdapter.onShopItemLongClickListener =
              object : ShopListAdapter.OnShopItemLongClickListener {
                  override fun onShopItemLongClick(shopItem: ShopItem) {
                      viewModel.changeIsEnableState(shopItem)
                  }
              }*/
        setUpLongClickListener()
        setUpClickListener()
        setUpSwipeListener(rvShopList)
    }

    // Functions section
    private fun setUpSwipeListener(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val shopItem = shopListAdapter.currentList[viewHolder.adapterPosition]
                viewModel.deleteShopItem(shopItem)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }

    private fun setUpClickListener() {
        shopListAdapter.onShopItemClickListenerLambda = {
            Log.d("MainActivity", it.toString())
        }
    }

    private fun setUpLongClickListener() {
        shopListAdapter.onShopItemLongClickListenerLambda = {
            viewModel.changeIsEnableState(it)
        }
    }
}