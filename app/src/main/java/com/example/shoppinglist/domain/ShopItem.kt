package com.example.shoppinglist.domain

data class ShopItem(
    val id: Int,
    val name: String,
    val isEnabled: Boolean,
    val count: Int
)
