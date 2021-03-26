package com.example.nm1.src.main.home.nest.notice.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityVoteBinding

class VoteActivity : BaseActivity<ActivityVoteBinding>(ActivityVoteBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.voteToolbar.toolbarBack)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.voteToolbar.toolbarTitle.text = "투표"
    }
}