package com.ppb.getfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.FirebaseApp
import com.google.firebase.database.*
import com.ppb.getfirebase.fitur.EditActivity
import com.ppb.getfirebase.fitur.InsertActivity

class MainActivity : AppCompatActivity() {

    private lateinit var userRecyclerView: RecyclerView
    private lateinit var adapter: MahasiswaAdapter
    private lateinit var userList: ArrayList<Mahasiswa>
    private lateinit var database: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val insertButton: Button = findViewById(R.id.btn_insert_main)
        insertButton.setOnClickListener {
            insertMahasiswa()
        }

        FirebaseApp.initializeApp(this)

        userRecyclerView = findViewById(R.id.rv_main)
        userRecyclerView.layoutManager = LinearLayoutManager(this)

        userList = ArrayList()
        adapter = MahasiswaAdapter(userList, this, ::editMahasiswa, ::deleteMahasiswa)
        userRecyclerView.adapter = adapter

        database = FirebaseDatabase.getInstance().getReference("mahasiswa")

        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList.clear()

                for (snapshot in dataSnapshot.children) {
                    val nim = snapshot.child("nim").getValue(String::class.java)
                    val mahasiswa = snapshot.getValue(Mahasiswa::class.java)
                    mahasiswa?.let {
                        it.nim = nim
                        it.nim?.let {
                            nim -> it.nim = nim
                            userList.add(it)
                        }
                    }
                }

                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("DatabaseError", "Database operation cancelled: ${error.message}")
            }
        })
    }

    private fun editMahasiswa(mahasiswa: Mahasiswa) {

        val intent = Intent(this, EditActivity::class.java)
        intent.putExtra("nama", mahasiswa.nama)
        intent.putExtra("nim", mahasiswa.nim)
        intent.putExtra("telp", mahasiswa.telepon)

        startActivity(intent)
    }

    private fun deleteMahasiswa(mahasiswa: Mahasiswa) {

        val userId = mahasiswa.nim
        userId?.let {
            database.child(it).removeValue()
        }
    }

    private fun insertMahasiswa() {

        val intent = Intent(this, InsertActivity::class.java)
        startActivity(intent)
    }
}