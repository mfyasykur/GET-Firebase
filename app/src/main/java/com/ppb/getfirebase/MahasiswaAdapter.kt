package com.ppb.getfirebase

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

@Suppress("DEPRECATION")
class MahasiswaAdapter(
    private val userList: ArrayList<Mahasiswa>,
    private val context: Context,
    private val editClickListener: (Mahasiswa) -> Unit,
    private val deleteClickListener: (Mahasiswa) -> Unit): RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder>() {

    private val database: DatabaseReference = FirebaseDatabase.getInstance().reference.child("mahasiswa")

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nama: TextView = itemView.findViewById(R.id.tv_nama)
        val nim: TextView = itemView.findViewById(R.id.tv_nim)
        val telepon: TextView = itemView.findViewById(R.id.tv_telp)
        val btnEdit: ImageButton = itemView.findViewById(R.id.ib_edit)
        val btnDelete: ImageButton = itemView.findViewById(R.id.ib_delete)

        init {
            btnEdit.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val mahasiswa = userList[position]
                    editClickListener.invoke(mahasiswa)
                }
            }

            btnDelete.setOnClickListener {
                val position = adapterPosition

                if (position != RecyclerView.NO_POSITION) {
                    val mahasiswa = userList[position]
                    deleteClickListener.invoke(mahasiswa)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)

        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userList[position]
        holder.nama.text = currentItem.nama
        holder.nim.text = "NIM: ${currentItem.nim}"
        holder.telepon.text = currentItem.telepon

        holder.btnEdit.setOnClickListener {
            editClickListener.invoke(currentItem)
        }
        holder.btnDelete.setOnClickListener {
            deleteClickListener.invoke(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}