package com.ayberk.e_commerceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.e_commerceapp.R
import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.databinding.ItemBagfavoriteBinding
import com.bumptech.glide.Glide


class BagAdapter() : RecyclerView.Adapter<BagAdapter.BagFavoriteViewHolder>() {

    var bagfavoriteList: List<FavoriteProducts>? = null
    var onDeleteClickListener: ((FavoriteProducts) -> Unit)? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BagAdapter.BagFavoriteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemBagfavoriteBinding.inflate(inflater, parent, false)
        return BagFavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BagAdapter.BagFavoriteViewHolder, position: Int) {
        bagfavoriteList?.let {
            if (it.isNotEmpty()) {
                holder.bind(it[position])
            }
        }
    }

    override fun getItemCount(): Int {
        return bagfavoriteList?.size ?: 0
    }

    inner class BagFavoriteViewHolder(private val binding: ItemBagfavoriteBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(bagproducts: FavoriteProducts) {

            binding.apply {

                val price = bagproducts.price
                val formattedPrice = when (price) {
                    1.700 -> "1.700 ₺"
                    else -> "${price.toString()} ₺"
                }

                txtbagproductsname.text = bagproducts.title
                txtProductPrice.text = formattedPrice
                txtImageDescription.text = bagproducts.description.toString()
                Glide.with(imgBagFavorite)
                    .load(bagproducts.imageOne)
                    .error(R.drawable.mavilogo)
                    .centerCrop()
                    .into(imgBagFavorite)

             //   imgDelete.setOnClickListener {
               //     onDeleteClickListener?.invoke(rocketFav)
               // }
            }
        }
    }

    fun updateList(list: List<FavoriteProducts>) {
        bagfavoriteList = list
        notifyDataSetChanged()
    }
}