package com.ayberk.e_commerceapp.data.source

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.model.Products

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorite(product: FavoriteProducts)

    @Query("SELECT * FROM favorites")
    fun getFavorites(): List<FavoriteProducts>

    @Delete
    fun deleteFavorite(bag: FavoriteProducts)

    @Query("DELETE FROM favorites")
    fun clearBags()


    @Query("SELECT COUNT(*) FROM favorites WHERE id = :id")
    fun checkIfDataExists(id: String): Int
}