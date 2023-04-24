package com.example.hilt.ui.main.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hilt.R
import com.example.hilt.data.model.User
import com.mindorks.framework.mvvm.ui.main.adapter.MainAdapter
import com.example.hilt.ui.main.viewmodel.MainViewModel
import com.mindorks.framework.mvvm.utils.Status
import dagger.hilt.android.AndroidEntryPoint
import androidx.activity.viewModels
import android.Manifest
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.example.hilt.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val mainViewModel : MainViewModel by viewModels()
    private lateinit var adapter: MainAdapter
    lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        binding.viewModel=mainViewModel
        binding.lifecycleOwner=this
        makeApiCall()
        requestPermission()
        setupUI()
        setupObserver()

    }

    private fun makeApiCall() {
        mainViewModel.fetchUsers()
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.INTERNET),
            101)
        ActivityCompat.requestPermissions(this,
            arrayOf(Manifest.permission.ACCESS_NETWORK_STATE),
            101)
    }
    private fun setupUI() {

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                binding.recyclerView.context,
                (binding.recyclerView.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.recyclerView.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.users.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { users -> renderList(users) }
                    binding.recyclerView.visibility = View.VISIBLE
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.recyclerView.visibility = View.GONE
                }
                Status.ERROR -> {
                    //Handle Error
                    binding.progressBar.visibility = View.GONE
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    private fun renderList(users: List<User>) {
        adapter.addData(users)
        adapter.notifyDataSetChanged()
    }
}
