package com.example.nm1.src.main.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentHomeBinding
import com.example.nm1.src.main.home.nest.NestActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind,R.layout.fragment_home) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.homeTvOwner.setOnClickListener {
            val intent = Intent(activity, NestActivity::class.java)
            startActivity(intent)
        }
    }
}