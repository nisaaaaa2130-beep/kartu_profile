package com.example.belajarmobile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    companion object {
        const val PREFS_PROFIL = "profil"
        const val KEY_NAMA_TERAKHIR = "nama_terakhir"
        const val KEY_EMAIL_TERAKHIR = "email_terakhir"
        const val KEY_TAHUN_LAHIR_TERAKHIR = "tahun_lahir_terakhir"
        const val KEY_UMUR_TERAKHIR = "umur_terakhir"
        const val KEY_KATEGORI_TERAKHIR = "kategori_terakhir"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etNama = findViewById<EditText>(R.id.etNama)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etTahunLahir = findViewById<EditText>(R.id.etTahunLahir)
        val btnSimpan = findViewById<Button>(R.id.btnSimpan)
        val btnBatal = findViewById<Button>(R.id.btnBatal)
        val btnHapusSimpan = findViewById<Button>(R.id.btnHapusSimpan)
        val btnLihatPengguna = findViewById<Button>(R.id.btnLihatPengguna)

        val prefs = getSharedPreferences(PREFS_PROFIL, Context.MODE_PRIVATE)
        val namaTersimpan = prefs.getString(KEY_NAMA_TERAKHIR, "") ?: ""
        val emailTersimpan = prefs.getString(KEY_EMAIL_TERAKHIR, "") ?: ""
        val tahunLahirTersimpan = prefs.getInt(KEY_TAHUN_LAHIR_TERAKHIR, -1)

        if (namaTersimpan.isNotEmpty()) {
            etNama.setText(namaTersimpan)
        }
        if (emailTersimpan.isNotEmpty()) {
            etEmail.setText(emailTersimpan)
        }
        if (tahunLahirTersimpan > 0) {
            etTahunLahir.setText(tahunLahirTersimpan.toString())
        }

        btnSimpan.setOnClickListener {
            val nama = etNama.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val teksTahun = etTahunLahir.text.toString().trim()

            if (nama.isEmpty()) {
                etNama.error = "Nama tidak boleh kosong"
                etNama.requestFocus()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                etEmail.error = "Email tidak boleh kosong"
                etEmail.requestFocus()
                return@setOnClickListener
            }
            if (!email.contains("@") || !email.contains(".")) {
                etEmail.error = "Format email belum benar (contoh: nama@mail.com)"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            val tahunLahir = teksTahun.toIntOrNull()
            val tahunSekarang = Calendar.getInstance().get(Calendar.YEAR)
            if (tahunLahir == null) {
                etTahunLahir.error = "Tahun lahir harus berupa angka, mis. 2006"
                etTahunLahir.requestFocus()
                return@setOnClickListener
            }
            if (tahunLahir < 1950 || tahunLahir > tahunSekarang) {
                etTahunLahir.error = "Tahun lahir harus antara 1950 dan $tahunSekarang"
                etTahunLahir.requestFocus()
                return@setOnClickListener
            }

            val umur = tahunSekarang - tahunLahir
            val kategori = when (umur) {
                in 0..12 -> "Anak"
                in 13..17 -> "Remaja"
                in 18..59 -> "Dewasa"
                else -> "Lansia"
            }

            prefs.edit()
                .putString(KEY_NAMA_TERAKHIR, nama)
                .putString(KEY_EMAIL_TERAKHIR, email)
                .putInt(KEY_TAHUN_LAHIR_TERAKHIR, tahunLahir)
                .putInt(KEY_UMUR_TERAKHIR, umur)
                .putString(KEY_KATEGORI_TERAKHIR, kategori)
                .apply()

            Toast.makeText(this, "Halo, $nama! ($umur tahun • $kategori)", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, SecondActivity::class.java)
            intent.putExtra(SecondActivity.EXTRA_NAMA, nama)
            intent.putExtra(SecondActivity.EXTRA_EMAIL, email)
            intent.putExtra(SecondActivity.EXTRA_UMUR, umur)
            intent.putExtra(SecondActivity.EXTRA_KATEGORI, kategori)
            startActivity(intent)
        }

        btnBatal.setOnClickListener {
            etNama.text.clear()
            etEmail.text.clear()
            etTahunLahir.text.clear()
            etNama.error = null
            etEmail.error = null
            etTahunLahir.error = null
            etNama.requestFocus()
            Toast.makeText(this, "Formulir dibersihkan", Toast.LENGTH_SHORT).show()
        }

        btnHapusSimpan.setOnClickListener {
            prefs.edit().clear().apply()
            etNama.text.clear()
            etEmail.text.clear()
            etTahunLahir.text.clear()
            etNama.error = null
            etEmail.error = null
            etTahunLahir.error = null
            etNama.requestFocus()
            Toast.makeText(this, "Data tersimpan dihapus", Toast.LENGTH_SHORT).show()
        }

        btnSimpan.setOnLongClickListener {
            Toast.makeText(this, "Tekan biasa tombol Simpan untuk menyimpan & pindah layar", Toast.LENGTH_SHORT).show()
            true
        }

        btnLihatPengguna.setOnClickListener {
            val intent = Intent(this, UserListActivity::class.java)
            startActivity(intent)
        }
    }
}