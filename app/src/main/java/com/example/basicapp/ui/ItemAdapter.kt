package com.example.basicapp.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.R
import com.example.basicapp.databinding.ItemViewBinding
import com.example.basicapp.model.Item

class ItemAdapter(
    private var itemList: List<Item>
) : RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {

    class ItemViewHolder(val binding: ItemViewBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<ItemViewBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_view, parent, false
        )
        return ItemViewHolder(binding)
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val currentItem = itemList[position]
        holder.binding.item = currentItem
    }

    fun submitList(newItemList: List<Item>) {
        this.itemList = newItemList
        notifyDataSetChanged()
    }
}