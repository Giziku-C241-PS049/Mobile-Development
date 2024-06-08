package com.example.giziku.reccomendation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.example.giziku.R
import com.example.giziku.databinding.FragmentHomeBinding
import com.example.giziku.databinding.FragmentReccomendationBinding


class ReccomendationFragment : Fragment() {
    //    private lateinit var homeViewModel: HomeViewModel
    private lateinit var binding: FragmentReccomendationBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReccomendationBinding.inflate(layoutInflater,container,false)
//        homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, {
//            textView.text = it
//        })
//        return root
        return binding.root
    }
}