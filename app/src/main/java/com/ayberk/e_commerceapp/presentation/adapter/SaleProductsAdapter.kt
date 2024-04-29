package com.ayberk.e_commerceapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ayberk.e_commerceapp.data.model.Products
import com.ayberk.e_commerceapp.databinding.ItemSaleproductBinding
import com.bumptech.glide.Glide

class SaleProductsAdapter : RecyclerView.Adapter<SaleProductsAdapter.ProductsViewHolder>() {

    private val list = ArrayList<Products>()

    var onProductClick: (Products) -> Unit = {}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        ProductsViewHolder(
            ItemSaleproductBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) =
        holder.bind(list[position])

    inner class ProductsViewHolder(private var binding: ItemSaleproductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Products) {

            with(binding) {

                product = item

                Glide.with(itemView.context)
                    .load(item.imageOne)
                    .centerCrop()
                    .into(imgProduct)

                imgProduct.setOnClickListener {
                    onProductClick(item)
                }
            }

        }
    }

    override fun getItemCount() = list.size

    fun updateList(updatedList: List<Products>) {
        list.clear()
        list.addAll(updatedList)
        notifyItemRangeInserted(0, updatedList.size)
    }
}