package com.ppb.getfirebase.fitur

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.ppb.getfirebase.R

class EditActivity : AppCompatActivity() {

    private lateinit var inputNim: TextInputEditText
    private lateinit var inputNama: TextInputEditText
    private lateinit var inputTelepon: TextInputEditText
    private lateinit var btnUpdate: Button
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val nim = intent.getStringExtra("nim")!!
        val nama = intent.getStringExtra("nama")!!
        val telepon = intent.getStringExtra("telepon")!!

        inputNama = findViewById(R.id.input_nama)
        inputNim = findViewById(R.id.input_nim)
        inputTelepon = findViewById(R.id.input_telepon)
        btnUpdate = findViewById(R.id.btn_update)

        database = FirebaseDatabase.getInstance().getReference("mahasiswa")

        inputNim.setText(nim)
        inputNama.setText(nama)
        inputTelepon.setText(telepon)

        btnUpdate.setOnClickListener {
            val updatedNama = inputNama.text.toString().trim()
            val updatedTelepon = inputTelepon.text.toString().trim()

            if (updatedNama.isNotEmpty() && updatedTelepon.isNotEmpty()) {
                val updatedData = HashMap<String, Any>()
                updatedData["nama"] = updatedNama
                updatedData["nim"] = nim
                updatedData["telepon"] = updatedTelepon

                database.child(nim).updateChildren(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this@EditActivity, "Data updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener {
                        Toast.makeText(this@EditActivity, "Failed to update data", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(this@EditActivity, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}