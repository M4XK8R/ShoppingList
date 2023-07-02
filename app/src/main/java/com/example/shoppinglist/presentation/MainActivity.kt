package com.example.shoppinglist.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppinglist.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

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

        val buttonAdd = findViewById<FloatingActionButton>(R.id.buttonAddShopItem)
        buttonAdd.setOnClickListener {
            val intent = ShopItemActivity.newIntentAddShopItem(this)
            startActivity(intent)
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
            val intent = ShopItemActivity.newIntentEditShopItem(this, it.id)
            startActivity(intent)
        }
    }

    private fun setUpLongClickListener() {
        shopListAdapter.onShopItemLongClickListenerLambda = {
            viewModel.changeIsEnableState(it)
        }
    }
}