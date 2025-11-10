package com.example.numberlistapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NumberAdapter(private var numbers: List<Int>) : RecyclerView.Adapter<NumberAdapter.NumberViewHolder>() {

    class NumberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNumber: TextView = itemView.findViewById(R.id.tvNumber)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumberViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_number, parent, false)
        return NumberViewHolder(view)
    }

    override fun onBindViewHolder(holder: NumberViewHolder, position: Int) {
        holder.tvNumber.text = numbers[position].toString()
    }

    override fun getItemCount(): Int {
        return numbers.size
    }

    // Hàm để cập nhật dữ liệu cho Adapter
    fun updateData(newNumbers: List<Int>) {
        this.numbers = newNumbers
        notifyDataSetChanged() // Báo cho RecyclerView biết dữ liệu đã thay đổi
    }
}
