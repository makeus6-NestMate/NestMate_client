package com.example.nm1.src.register

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.nm1.R
import kotlinx.android.synthetic.main.register_one.*
import kotlinx.android.synthetic.main.toolbar_back.*

class RegisterOne : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_one)
        toolbar_title.text="회원가입"

        register_one_btn.setOnClickListener {
            val intent = Intent(this, RegisterTwo::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
            finish()
        }
    }
}