package com.example.shoppinglist.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.shoppinglist.domain.ShopItem

class ShopListDiffCallback(
    private val oldLIst: List<ShopItem>,
    private val newList: List<ShopItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldLIst.size
    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldShopItem = oldLIst[oldItemPosition]
        val newShopItem = newList[newItemPosition]
        return oldShopItem.id == newShopItem.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldShopItem = oldLIst[oldItemPosition]
        val newShopItem = newList[newItemPosition]
        return oldShopItem == newShopItem
    }
}