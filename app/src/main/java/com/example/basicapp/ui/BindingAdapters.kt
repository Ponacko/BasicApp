package com.example.basicapp.ui

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.model.Item

@BindingAdapter("items")
fun bindRecyclerViewItems(recyclerView: RecyclerView, items: List<Item>?) {
    val adapter = recyclerView.adapter as? ItemAdapter
    items?.let {
        adapter?.submitList(it)
    }
}

@BindingAdapter("app:visibility")
fun setVisibility(view: View, isVisible: Boolean) {
    view.visibility = if (isVisible) View.VISIBLE else View.GONE
}