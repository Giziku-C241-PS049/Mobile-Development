package com.example.giziku.view.news.detail

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.example.giziku.R
import com.example.giziku.view.news.main.News

class DetailNewsActivity : AppCompatActivity(){

    companion object {
        const val KEY_NEWS = "key_news"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_news)

        val dataNews = if (Build.VERSION.SDK_INT >= 33) {
            intent.getParcelableExtra<News>(KEY_NEWS, News::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent.getParcelableExtra<News>(KEY_NEWS)
        }

        val detailImage: ImageView = findViewById(R.id.img_detail)
        val detailTitles: TextView = findViewById(R.id.news_titles)
        val detailDesc: TextView = findViewById(R.id.news_desc)
        val detailAuth: TextView = findViewById(R.id.author_tv)
        val detailPublish: TextView = findViewById(R.id.tv_publish_time)


        if (dataNews != null) {
            detailTitles.text = dataNews.titles
            detailDesc.text = dataNews.description
            detailImage.setImageResource(dataNews.photo)
            detailAuth.text = dataNews.author
            detailPublish.text = dataNews.publish
        }

    }
}