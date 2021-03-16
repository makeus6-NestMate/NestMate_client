package com.example.nm1.src.main

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityMainBinding
import com.example.nm1.src.login.LoginActivity
import com.example.nm1.src.main.home.HomeFragment
import com.example.nm1.src.main.mypage.MyFragment
import com.example.nm1.src.register.RegisterOneActivity

class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate)  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        binding.tabBottom.run {
            setOnNavigationItemSelectedListener {
                when(it.itemId) {
                    R.id.home -> { changeFragment(HomeFragment()) }
                    R.id.alarm -> {
                        val intent = Intent(this@MainActivity, LoginActivity::class.java)
                        startActivity(intent)
                    }
                    R.id.my -> {
                        if(ApplicationClass.sSharedPreferences.getString("JWT", "na") == "na"){
                            val intent = Intent(this@MainActivity, RegisterOneActivity::class.java)
                            startActivity(intent)
                        }else{
                            changeFragment(MyFragment())
                        }
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
}