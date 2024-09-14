package com.example.basicapp.ui

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemAdapter(emptyList())

        setOnClickListeners()
        setObservers()
    }

    private fun setObservers() {
        viewModel.items.observe(this) { items ->
            displayList()
            (binding.recyclerView.adapter as ItemAdapter).submitList(items.toMutableList())
        }
        viewModel.error.observe(this) { errorMessage ->
            displayList()
            showErrorDialog(errorMessage)
        }
        viewModel.user.observe(this) { user ->
            binding.loadedUserText.text = "${user.firstName} ${user.lastName}"
        }
    }

    private fun setOnClickListeners() {
        binding.fetchButton.setOnClickListener {
            displayLoading()
            viewModel.fetchItems()
        }
        binding.saveButton.setOnClickListener {
            val user = viewModel.createUserFromInput(binding.firstName.text.toString(),
                binding.lastName.text.toString())
            viewModel.saveUser(user)
        }

        binding.loadButton.setOnClickListener {
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
        binding.progressBar.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    private fun displayLoading() {
        binding.progressBar.visibility = View.VISIBLE
        binding.recyclerView.visibility = View.GONE
    }
}