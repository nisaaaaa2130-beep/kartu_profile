package com.example.belajarmobile

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("users")
    fun getUsers(): Call<List<User>>

    @GET("quotes")
    fun getQuotes(): Call<QuotesResponse>
}