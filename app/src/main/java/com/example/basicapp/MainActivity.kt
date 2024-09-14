package com.example.basicapp

import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.model.Item
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter =
            ItemAdapter(emptyList())
        val fetchButton = findViewById<Button>(R.id.fetchButton)
        fetchButton.setOnClickListener {
            viewModel.fetchItems()
        }
        viewModel.items.observe(this) { items ->
            (recyclerView.adapter as ItemAdapter).submitList(items.toMutableList())
        }

    }
}