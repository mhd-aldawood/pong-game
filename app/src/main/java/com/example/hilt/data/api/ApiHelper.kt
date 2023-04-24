package com.example.hilt.data.api

import com.example.hilt.data.model.User
import retrofit2.Response

interface ApiHelper {

    suspend fun getUsers(): Response<List<User>>
//    suspend fun getUsers(): Response<List<User>>
}