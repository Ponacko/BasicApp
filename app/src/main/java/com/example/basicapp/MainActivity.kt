package com.example.basicapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val fetchButton = findViewById<Button>(R.id.fetchButton)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(emptyList())

        fetchButton.setOnClickListener {
            // Show loading indicator while data is being fetched
            progressBar.visibility = View.VISIBLE
            viewModel.fetchItems()
        }
        viewModel.items.observe(this) { items ->
            // Hide loading indicator when data is available
            progressBar.visibility = View.GONE
            (recyclerView.adapter as ItemAdapter).submitList(items.toMutableList())
        }

    }
}