package com.example.basicapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.basicapp.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var fetchButton: Button
    private lateinit var saveButton: Button
    private lateinit var loadButton: Button
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var loadedUserText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViews()

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(emptyList())

        setOnClickListeners()
        setObservers()
    }

    private fun findViews() {
        recyclerView = findViewById(R.id.recyclerView)
        progressBar = findViewById(R.id.progressBar)
        fetchButton = findViewById(R.id.fetchButton)
        saveButton = findViewById(R.id.saveButton)
        loadButton = findViewById(R.id.loadButton)
        firstNameEditText = findViewById(R.id.firstName)
        lastNameEditText = findViewById(R.id.lastName)
        loadedUserText = findViewById(R.id.loadedUserText)
    }

    private fun setObservers() {
        viewModel.items.observe(this) { items ->
            displayList()
            (recyclerView.adapter as ItemAdapter).submitList(items.toMutableList())
        }
        viewModel.error.observe(this) { errorMessage ->
            displayList()
            showErrorDialog(errorMessage)
        }
        viewModel.user.observe(this) { user ->
            loadedUserText.text = "${user.firstName} ${user.lastName}"
        }
    }

    private fun setOnClickListeners() {
        fetchButton.setOnClickListener {
            displayLoading()
            viewModel.fetchItems()
        }
        saveButton.setOnClickListener {
            val user = viewModel.createUserFromInput(firstNameEditText.text.toString(),
                lastNameEditText.text.toString())
            viewModel.saveUser(user)
        }

        loadButton.setOnClickListener {
            viewModel.loadUser(1)
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

    private fun displayList() {
        progressBar.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
    }

    private fun displayLoading() {
        progressBar.visibility = View.VISIBLE
        recyclerView.visibility = View.GONE
    }
}