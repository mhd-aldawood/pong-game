package com.example.hilt.ui.main.viewmodel


import androidx.lifecycle.*
import com.example.hilt.utils.NetworkHelper
import com.example.hilt.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

    private val networkHelper: NetworkHelper
) : ViewModel() {



}