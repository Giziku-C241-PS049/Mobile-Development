package com.example.giziku.reccomendation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.giziku.R


class ReccomendationFragment : Fragment() {
    private lateinit var reccomendationViewModel: ReccomendationViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        reccomendationViewModel =
            ViewModelProvider(this).get(reccomendationViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_reccomendation, container, false)
        val textView: TextView = root.findViewById(R.id.text_reccomendation)
        reccomendationViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })
        return root
    }
}