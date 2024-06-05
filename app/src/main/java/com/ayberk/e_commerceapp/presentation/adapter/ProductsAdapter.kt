package com.ayberk.e_commerceapp.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.e_commerceapp.R
import com.ayberk.e_commerceapp.data.model.FavoriteProducts
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.data.source.FavoriteDao
import com.ayberk.e_commerceapp.databinding.ItemProductsBinding
import com.ayberk.e_commerceapp.domain.usecase.event.BagEvent
import com.bumptech.glide.Glide
import java.text.DecimalFormat

class ProductsAdapter(
    private val event: (BagEvent) -> Unit,
    private val dataDao: FavoriteDao
) : RecyclerView.Adapter<ProductsAdapter.RocketViewHolder>() {

    private var productsList: List<Products>? =  null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductsBinding.inflate(inflater, parent, false)
        return RocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        productsList?.let { bagfavitem ->
            if (bagfavitem.isNotEmpty()) {
                val bagfavitem = bagfavitem[position]
                holder.bind(bagfavitem)
            }
        }
    }

    override fun getItemCount(): Int {
        return productsList?.size ?: 0
    }

    inner class RocketViewHolder(private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Products) {
            binding.apply {

                val price = product.price
                val formattedPrice = when (price) {
                    1.700 -> "1.700 ₺"
                    else -> "${price.toString()} ₺"
                }

                txtProductPrice.text = formattedPrice

                txtProductTitle.text = product.title

                 Glide.with(imgProduct.rootView)
                     .load(product.imageOne)
                     .error(R.drawable.mavilogo)
                     .centerCrop()
                     .into(imgProduct)

                if (product.rate!! > 4){
                    imgRate.setImageResource(R.drawable.star)
                }
                else {
                    imgRate.setImageResource(R.drawable.starhalf)
                }

                binding.imgBage.setOnClickListener {
                    val id = product.id
                    val description = product.description
                    val imageOne = product.imageOne
                    val imageTwo = product.imageTwo
                    val imageThree = product.imageThree
                    val price = product.price
                    val rate = product.rate
                    val title = product.title
                    val isFavorite = product.isFavorite
                    val salePrice = product.salePrice

                    val newProduct = FavoriteProducts(
                        id = id,
                        description = description,
                        imageOne = imageOne,
                        imageTwo = imageTwo,
                        imageThree = imageThree,
                        price = price,
                        rate = rate,
                        title = title,
                        isFavorite = isFavorite,
                        salePrice = salePrice,
                    )
                    event(BagEvent.UpsertDeleteBag(newProduct))
                }
            }
        }
    }

    fun setProductsList(newList: List<Products>) {
        productsList = newList
        notifyDataSetChanged()
    }
}