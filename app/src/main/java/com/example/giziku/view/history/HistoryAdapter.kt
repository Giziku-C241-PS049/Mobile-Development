package com.example.giziku.view.history

import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.giziku.R
import com.example.giziku.database.History
import com.example.giziku.databinding.ItemRowHistoryBinding

class HistoryAdapter (private val listHistory: List<History>,
                      private val deleteClick: (History) -> Unit) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    inner class HistoryViewHolder(val binding: ItemRowHistoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.btnDelete.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val history = listHistory[position]
                    deleteClick(history)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemRowHistoryBinding.inflate(inflater, parent, false)
        return HistoryViewHolder(binding)
    }

    override fun getItemCount(): Int = listHistory.size

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        val history = listHistory[position]
        holder.binding.imgHistory.setImageURI(history.imageUri.let { Uri.parse(it) })
        holder.binding.newsResult.text = history.result
        holder.binding.confidenceFood.text = history.foodName.toString()
        holder.binding.confidenceText.text = holder.itemView.context.getString(R.string.food_name)
    }
}