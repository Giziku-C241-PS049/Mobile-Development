package com.example.giziku.view.history

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.giziku.ResultActivity
import com.example.giziku.databinding.ActivityHistoryBinding

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.historyCard.setOnClickListener()
        {val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)}

        binding.btnDelete.setOnClickListener() {
            binding.historyCard.visibility = View.GONE
        }
    }
}
