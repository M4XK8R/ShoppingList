package com.example.shoppinglist.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.shoppinglist.R
import com.example.shoppinglist.domain.ShopItem

class ShopListAdapter :
    androidx.recyclerview.widget.ListAdapter<ShopItem, ShopItemViewHolder>(
        ShopItemDiffCallback()
    ) {

    private var count = 0

//    var onShopItemLongClickListener: OnShopItemLongClickListener? = null
    var onShopItemLongClickListenerLambda: ((ShopItem) -> Unit)? = null
    var onShopItemClickListenerLambda: ((ShopItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
//        Log.d("ShopListAdapter", "onCreateViewHolder ${++count}")
        val layout = when (viewType) {
            VIEW_TYPE_ENABLED -> R.layout.item_shop_enabled
            VIEW_TYPE_DISABLED -> R.layout.item_shop_disabled
            else -> throw RuntimeException("Unknown viewType: $viewType")
        }
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return ShopItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ShopItemViewHolder, position: Int) {
        Log.d("ShopListAdapter", "onBindViewHolder ${++count}")
        val shopItem = getItem(position)
        holder.apply {
            tvName.text = shopItem.name
            tvCount.text = shopItem.count.toString()
            itemView.setOnLongClickListener {
//            onShopItemLongClickListener?.onShopItemLongClick(shopItem)
                onShopItemLongClickListenerLambda?.invoke(shopItem)
                true
            }
            itemView.setOnClickListener {
                onShopItemClickListenerLambda?.invoke(shopItem)
                true
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val shopItem = getItem(position)
        return if (shopItem.isEnabled) VIEW_TYPE_ENABLED else VIEW_TYPE_DISABLED
    }

    companion object {
        const val VIEW_TYPE_ENABLED = 100
        const val VIEW_TYPE_DISABLED = 101

        const val VH_MAX_POOL_SIZE = 10
    }

   /* interface OnShopItemLongClickListener {
        fun onShopItemLongClick(shopItem: ShopItem)
        }
   */
}