package com.ppb.getfirebase

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MahasiswaAdapter(private val userList: ArrayList<Mahasiswa>): RecyclerView.Adapter<MahasiswaAdapter.MyViewHolder>() {

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val nama: TextView = itemView.findViewById(R.id.tv_nama)
        val nim: TextView = itemView.findViewById(R.id.tv_nim)
        val telepon: TextView = itemView.findViewById(R.id.tv_telp)
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
        holder.nim.text = currentItem.nim
        holder.telepon.text = currentItem.telepon
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}