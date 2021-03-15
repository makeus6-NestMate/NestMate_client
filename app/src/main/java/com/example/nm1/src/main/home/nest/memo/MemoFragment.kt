package com.example.nm1.src.main.home.nest.memo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentMemoBinding
import kotlinx.android.synthetic.main.memo_delete_dialog.view.*
import kotlinx.android.synthetic.main.memo_item.view.*

class MemoFragment : BaseFragment<FragmentMemoBinding>(
    FragmentMemoBinding::bind,
    R.layout.fragment_memo
), MemoAddInterface, MemoDeleteInterface {


    var idx = 0
    var moveX = 0f
    var moveY = 0f
    var isItemMoving = false
    var xDelta: Int? = null
    var yDelta: Int? = null

    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)


        binding.memoAddBtn.setOnClickListener {
            val customDialog = MemoAddDialog(this)
            customDialog.show(parentFragmentManager, "test")
        }
    }

    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
    override fun onConfirmBtnClicked(message: String, color: Int?) {
        Log.d("로그", "This is MemoFragment${color}")

        var item = activity?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var target = item.inflate(R.layout.memo_item, null)
        target.memo_item_tv.text = message
        if(color != null){
            target.memo_item_layout.setBackgroundColor(resources.getColor(color))
        }


        target.setOnTouchListener { v, event ->
            var parent = view?.parent
            parent?.requestDisallowInterceptTouchEvent(true)

            when(event.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    moveX = v.x - event.rawX
                    moveY = v.y - event.rawY
                    Log.d("로그", "DOWN: ${isItemMoving}")
                }
                MotionEvent.ACTION_MOVE -> {
                    v.animate().x(event.rawX + moveX).y(event.rawY + moveY).setDuration(0).start()
                    Log.d("로그", "MOVE: ${isItemMoving}")
                }

                MotionEvent.ACTION_UP -> {
                    if (v.x < 0) {
                        v.x = 0f
                    } else if (v.x + v.width > binding.memoFrameLayout.width) {
                        v.x = (binding.memoFrameLayout.width - v.width).toFloat()
                    }

                    if (v.y < 0) {
                        v.y = 0f
                    } else if (v.y + v.height > binding.memoFrameLayout.height) {
                        v.y = (binding.memoFrameLayout.height - v.height).toFloat()
                    }
                }

            }


            true
        }

        target.memo_item_more_btn.setOnClickListener {
            Log.d("로그", "hahaha")
            val deleteDialog = MemoDeleteDialog(requireContext(), this)
            deleteDialog.show(childFragmentManager, deleteDialog.tag)
            true
        }



        var frame = binding.memoFrameLayout
        frame.addView(
            target, 0, ViewGroup.LayoutParams(
                (size!!.x * 0.4).toInt(),
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
        )
        idx++



    }

    override fun onCancelBtnClicked() {

    }

    override fun onDeleteClicked(flag: Boolean) {
        if(flag){
            Log.d("로그", "Delete Clicked!!")
        }
    }



}