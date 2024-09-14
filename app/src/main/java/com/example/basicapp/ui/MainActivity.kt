package com.example.basicapp.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.basicapp.R
import com.example.basicapp.databinding.ActivityMainBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModel()

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        val view = binding.root
        setContentView(view)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = ItemAdapter(emptyList())

        setObservers()
    }

    private fun setObservers() {
        viewModel.error.observe(this) { errorMessage ->
            showErrorDialog(errorMessage)
        }
    }

    private fun showErrorDialog(message: String?) {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.error))
            .setMessage(message ?: getString(R.string.an_unknown_error_occurred))
            .setPositiveButton(getString(R.string.ok)) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}