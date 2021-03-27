package com.example.nm1.src.main.home.nest.notice.vote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityVoteBinding

class VoteActivity : BaseActivity<ActivityVoteBinding>(ActivityVoteBinding::inflate) {
    val voteFragment = VoteFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setSupportActionBar(binding.voteToolbar.toolbarBack)
        supportActionBar!!.setDisplayShowTitleEnabled(false)
        binding.voteToolbar.toolbarTitle.text = "투표"

        val bundle = Bundle()
        bundle.putInt("roomId", intent.getIntExtra("roomId", -1))
        bundle.putInt("voteId", intent.getIntExtra("voteId", -1))
        voteFragment.arguments = bundle

        supportFragmentManager.beginTransaction().add(R.id.vote_framelayout, voteFragment).commit()
        //supportFragmentManager.beginTransaction().isAddToBackStackAllowed
    }

    fun replaceFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.vote_framelayout, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }


}