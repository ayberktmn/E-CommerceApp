package com.ayberk.e_commerceapp.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "favorites")
@Parcelize
data class FavoriteProducts(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "image_one")
    val imageOne: String?,

    @ColumnInfo(name = "image_two")
    val imageTwo: String?,

    @ColumnInfo(name = "image_three")
    val imageThree: String?,

    @ColumnInfo(name = "price")
    val price: Double?,

    @ColumnInfo(name = "rate")
    val rate: Double?,

    @ColumnInfo(name = "title")
    val title: String?,

    @ColumnInfo(name = "is_favorite")
    val isFavorite: Boolean = false,

    @ColumnInfo(name = "sale_price")
    val salePrice: Double?,
): Parcelable
