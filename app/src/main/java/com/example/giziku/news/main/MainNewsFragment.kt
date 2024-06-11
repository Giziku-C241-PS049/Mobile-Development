package com.example.giziku.news.main

import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.giziku.R
import com.example.giziku.databinding.FragmentMainNewsBinding
import com.example.giziku.news.News
import com.example.giziku.news.NewsAdapter
import com.example.giziku.news.detail.DetailNewsActivity


class MainNewsFragment : Fragment() {
    private val list = ArrayList<News>()
    private lateinit var binding: FragmentMainNewsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainNewsBinding.inflate(layoutInflater,container,false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.rvNews.setHasFixedSize(true)
        list.addAll(getListNews())
        showRecyclerList()

    }

    private fun getListNews(): Collection<News> {
        val dataTitle = resources.getStringArray(R.array.news_title)
        val dataDescription = resources.getStringArray(R.array.news_desc)
        val dataPhoto = resources.obtainTypedArray(R.array.detail_img)
        val dataPublish = resources.getStringArray(R.array.publish_time)
        val dataAuth = resources.getStringArray(R.array.news_author)
        val listNews = ArrayList<News>()
        for (i in dataTitle.indices) {
            val news = News(dataTitle[i], dataDescription[i], dataPhoto.getResourceId(i, -1),dataAuth[i],dataPublish[i])
            listNews.add(news)
        }
        return listNews
    }

    private fun showRecyclerList() {
        binding.rvNews.layoutManager = LinearLayoutManager(requireContext())
        val listNewsAdapter = NewsAdapter(list)
        binding.rvNews.adapter = listNewsAdapter

        listNewsAdapter.setOnItemClickCallback((object: NewsAdapter.OnItemClickCallback{
            override fun onItemClicked(data: News) {
                val detailNewsActivity = Intent(requireActivity(), DetailNewsActivity::class.java)
                detailNewsActivity.putExtra("key_news", data.titles)
                detailNewsActivity.putExtra("key_news", data.photo)
                detailNewsActivity.putExtra("key_news", data.author)
                detailNewsActivity.putExtra("key_news", data.publish)
                detailNewsActivity.putExtra("key_news", data.description)
                startActivity(detailNewsActivity)
            }
        }))
    }
}