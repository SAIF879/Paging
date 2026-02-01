package com.example.paging.data.api

import com.example.paging.data.dto.RandomUserResponse
import retrofit2.http.GET

interface ApiService {

    @GET("api/?results=25&seed=abc")
    suspend fun getAllUsers(): RandomUserResponse
}
