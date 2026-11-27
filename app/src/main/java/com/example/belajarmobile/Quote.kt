package com.example.belajarmobile

data class Quote(
    val id: Int,
    val quote: String,
    val author: String
)

data class QuotesResponse(
    val quotes: List<Quote>
)