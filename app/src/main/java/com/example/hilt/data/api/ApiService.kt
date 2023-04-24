package com.mindorks.framework.mvvm.data.api

import com.example.hilt.data.model.User
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("users")
    suspend fun getUsers(): Response<List<User>>

}