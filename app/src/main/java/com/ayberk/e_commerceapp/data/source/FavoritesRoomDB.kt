package com.ayberk.e_commerceapp.data.source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.model.Products

@Database(entities = [FavoriteProducts::class], version = 1, exportSchema = false)
abstract class FavoritesRoomDB : RoomDatabase() {
    abstract fun productFavoriteDAO(): FavoriteDao
}