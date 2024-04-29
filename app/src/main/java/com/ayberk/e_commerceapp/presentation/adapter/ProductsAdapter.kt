package com.ayberk.e_commerceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.e_commerceapp.R
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.databinding.ItemProductsBinding
import com.bumptech.glide.Glide

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.RocketViewHolder>() {

    private var productsList: List<Products> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RocketViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProductsBinding.inflate(inflater, parent, false)
        return RocketViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RocketViewHolder, position: Int) {
        holder.bind(productsList[position])
    }

    override fun getItemCount(): Int {
        return productsList.size
    }

    inner class RocketViewHolder(private val binding: ItemProductsBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Products) {
            binding.apply {
                txtProductTitle.text = product.title
                txtProductPrice.text = product.price.toString()

                 Glide.with(imgProduct.context)
                     .load(product.imageOne)
                     .error(R.drawable.ic_launcher_background)
                     .centerCrop()
                     .into(imgProduct)

                if (product.rate > 4){
                    imgRate.setImageResource(R.drawable.star)
                }
                else {
                    imgRate.setImageResource(R.drawable.starhalf)
                }
            }
        }
    }

    fun setProductsList(newList: List<Products>) {
        productsList = newList
        notifyDataSetChanged()
    }
}