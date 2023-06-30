package com.ppb.getfirebase.fitur

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ppb.getfirebase.Mahasiswa
import com.ppb.getfirebase.R

class InsertActivity : AppCompatActivity() {

    private lateinit var inputNim: TextInputEditText
    private lateinit var inputNama: TextInputEditText
    private lateinit var inputTelepon: TextInputEditText
    private lateinit var btnTambah: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)

        inputNim = findViewById(R.id.input_nim_ins)
        inputNama = findViewById(R.id.input_nama_ins)
        inputTelepon = findViewById(R.id.input_telepon_ins)
        btnTambah = findViewById(R.id.btn_insert)

        database = FirebaseDatabase.getInstance().getReference("mahasiswa")

        btnTambah.setOnClickListener {
            val nim = inputNim.text.toString()
            val nama = inputNama.text.toString()
            val telepon = inputTelepon.text.toString()

            if (nim.isNotEmpty() && nama.isNotEmpty() && telepon.isNotEmpty()) {
                insertMahasiswa(nim, nama, telepon)
            } else {
                Toast.makeText(this@InsertActivity, "Mohon isi data terlebih dahulu", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun insertMahasiswa(nim: String, nama: String, telepon: String) {

        val mahasiswaId = database.push().key
        val mahasiswa = Mahasiswa(nama, nim, telepon)

        if (mahasiswaId != null) {

            database.child(mahasiswaId).setValue(mahasiswa)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data added successfully", Toast.LENGTH_SHORT).show()
                    finish()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add data", Toast.LENGTH_SHORT).show()
                    finish()
                }
        }
    }
}