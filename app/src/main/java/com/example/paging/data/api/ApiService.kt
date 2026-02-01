package com.example.paging.data.api

import com.example.paging.data.dto.RandomUserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("api/")
    suspend fun getAllUsers(
        @Query("page") page: Int,
        @Query("results") results: Int = 15,
        @Query("seed") seed: String = "abc"
    ): RandomUserResponse
}
