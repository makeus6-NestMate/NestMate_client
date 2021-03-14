package com.example.nm1.src.main.home.nest.todo

import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityTodoManagerBinding

class TodoManagerActivity : BaseActivity<ActivityTodoManagerBinding>(ActivityTodoManagerBinding::inflate) {
    private val isselected = Array(7){false}
    private val isrepeat = Array(2){false}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.back.setOnClickListener {
            finish()
        }

        isrepeat[0] = true

        binding.todoManagerBtnRepeat.setOnClickListener {
            binding.todoManagerBtnRepeat.setBackgroundResource(R.drawable.orange_button)
            binding.todoManagerBtnRepeat.setTextColor(
                ContextCompat.getColor(this, R.color.white
                )
            )
            binding.todoManagerBtnOne.setBackgroundResource(R.drawable.white_button)
            binding.todoManagerBtnOne.setTextColor(
                ContextCompat.getColor(
                    this, R.color.gray2
                )
            )


            isrepeat[0] = true
            isrepeat[1] = false
        }

        binding.todoManagerBtnOne.setOnClickListener {
            binding.todoManagerBtnOne.setBackgroundResource(R.drawable.orange_button)
            binding.todoManagerBtnOne.setTextColor(
                ContextCompat.getColor(
                    this, R.color.white
                )
            )
            binding.todoManagerBtnRepeat.setBackgroundResource(R.drawable.white_button)
            binding.todoManagerBtnRepeat.setTextColor(
                ContextCompat.getColor(
                    this, R.color.gray2
                )
            )

            isrepeat[0] = false
            isrepeat[1] = true
        }
    }
}