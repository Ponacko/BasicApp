package com.example.basicapp.ui

import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.R
import com.example.basicapp.data.User
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

@BindingAdapter("text")
fun setUserText(textView: TextView, user: User?) {
    val context = textView.context
    textView.text = user?.let {
        context.getString(R.string.saved_user, it.firstName, it.lastName)
    } ?: context.getString(R.string.no_user_saved)
}