package com.example.nm1.src.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityMainBinding
import com.example.nm1.src.main.alarm.AlarmFragment
import com.example.nm1.src.main.home.HomeFragment
import com.example.nm1.src.main.mypage.MyFragment

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding.tabBottom.itemIconTintList = null

        binding.tabBottom.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.home -> { changeFragment(HomeFragment()) }
                    R.id.alarm -> {
                        changeFragment(AlarmFragment())
                    }
                    R.id.my -> {
                        changeFragment(MyFragment())
                    }
                }
                true
            }
            selectedItemId = R.id.home
        }
    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager .beginTransaction().replace(R.id.outerFrame, fragment) .commit()
    }

    fun changeTipFragment(fragment: Fragment){
        supportFragmentManager .beginTransaction().replace(R.id.main_frame, fragment) .commit()
    }
}