package com.example.belajarmobile

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuoteListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quote_list)

        val rvQuotes = findViewById<RecyclerView>(R.id.rvQuotes)
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val tvStatusQuote = findViewById<TextView>(R.id.tvStatusQuote)

        rvQuotes.layoutManager = LinearLayoutManager(this)

        progressBar.visibility = View.VISIBLE
        tvStatusQuote.text = "Memuat kutipan..."

        // Pastikan properti quoteApi ada di RetrofitClient.kt dan mengarah ke https://dummyjson.com/
        RetrofitClient.quoteApi.getQuotes().enqueue(object : Callback<QuotesResponse> {
            override fun onResponse(
                call: Call<QuotesResponse>,
                response: Response<QuotesResponse>
            ) {
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    tvStatusQuote.text = ""
                    rvQuotes.adapter = QuoteAdapter(response.body()?.quotes ?: emptyList())
                } else {
                    tvStatusQuote.text = "Server menjawab dengan kode ${response.code()}"
                }
            }

            override fun onFailure(call: Call<QuotesResponse>, t: Throwable) {
                progressBar.visibility = View.GONE
                tvStatusQuote.text = "Gagal memuat data: ${t.message}"
                Toast.makeText(
                    this@QuoteListActivity,
                    "Periksa koneksi internet",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }
}