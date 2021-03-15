package com.example.nm1.src.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.nm1.R
import com.example.nm1.src.main.alarm.AlarmFragment
import com.example.nm1.src.main.home.HomeFragment
import com.example.nm1.src.main.mypage.MyFragment
import kotlinx.android.synthetic.main.outer_main.*
// Test !!!


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.outer_main)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        tabBottom.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.home -> { changeFragment(HomeFragment()) }
                    R.id.alarm -> { changeFragment(AlarmFragment()) }
                    R.id.my -> { changeFragment(MyFragment()) }
                }
                true
            }
            selectedItemId = R.id.home
        }
    }
    private fun changeFragment(fragment: Fragment) {
        supportFragmentManager .beginTransaction().replace(R.id.outerFrame, fragment) .commit()
    }
}