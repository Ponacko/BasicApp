package com.example.basicapp

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
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
            progressBar.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            viewModel.fetchItems()
        }
        viewModel.items.observe(this) { items ->
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            (recyclerView.adapter as ItemAdapter).submitList(items.toMutableList())
        }
        viewModel.error.observe(this) { errorMessage ->
            progressBar.visibility = View.GONE
            recyclerView.visibility = View.VISIBLE
            showErrorDialog(errorMessage)
        }
    }

    private fun showErrorDialog(message: String?) {
        AlertDialog.Builder(this)
            .setTitle("Error")
            .setMessage(message ?: "An unknown error occurred.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}