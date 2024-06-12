package com.example.giziku.news.detail

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.giziku.R
import com.example.giziku.news.main.News

class NewsAdapter (private val listNews: ArrayList<News>) : RecyclerView.Adapter<NewsAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_row_news, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int = listNews.size


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (titles, description, photo, auth,publish) = listNews[position]
        holder.imgPhoto.setImageResource(photo)
        holder.tvTitles.text = titles
        holder.tvAuth.text = auth
        holder.itemView.setOnClickListener {
            val intentDetail = Intent(holder.itemView.context, DetailNewsActivity::class.java)
            intentDetail.putExtra("key_news", listNews[holder.adapterPosition])
            holder.itemView.context.startActivity(intentDetail)
        }
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgPhoto: ImageView = itemView.findViewById(R.id.img_news)
        val tvAuth: TextView = itemView.findViewById(R.id.source_news)
        val tvTitles: TextView = itemView.findViewById(R.id.news_titles)

    }
    interface OnItemClickCallback {
        fun onItemClicked(data: News)
    }
}