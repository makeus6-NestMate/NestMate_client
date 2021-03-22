package com.example.nm1.src.main.home.nest.memo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.config.BaseResponse
import com.example.nm1.databinding.FragmentMemoBinding
import com.example.nm1.src.main.home.nest.memo.model.GetMemoResponse
import com.example.nm1.src.main.home.nest.memo.model.MemoData
import com.example.nm1.src.main.home.nest.memo.model.PatchMemoRequest
import com.example.nm1.src.main.home.nest.memo.model.PostMemoRequest
import com.jakewharton.rxbinding4.view.focusChanges
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.subscribeBy
import kotlinx.android.synthetic.main.memo_item.view.*
import retrofit2.Call
import java.util.concurrent.TimeUnit

class MemoFragment : BaseFragment<FragmentMemoBinding>(
    FragmentMemoBinding::bind,
    R.layout.fragment_memo
), MemoAddInterface, MemoDeleteInterface, MemoView {

    var idx = 0
    var moveX = 0f
    var moveY = 0f
    var isItemMoving = false
    var xDelta: Int? = null
    var yDelta: Int? = null

    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var toBeremoved = 0
    private lateinit var memoList: ArrayList<MemoData>

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

        MemoService(this).tryGetMemo(ApplicationClass.sSharedPreferences.getInt("roomId", -1))
    }


    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
    override fun onConfirmBtnClicked(message: String, color: String?) {
        Log.d("로그", "This is MemoFragment${color}")

        var item = activity?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        var target = item.inflate(R.layout.memo_item, null)
        target.memo_item_tv.text = message

        if(color != null){
            target.memo_item_layout.setBackgroundColor(Color.parseColor(color))
        }



        val request = PostMemoRequest(message, 0f, 0f, (color ?: "#ff9e81"))
        MemoService(this@MemoFragment).tryPostMemo(ApplicationClass.sSharedPreferences.getInt("roomId", 0), request)
    }

    override fun onCancelBtnClicked() {

    }

    override fun onDeleteClicked(flag: Boolean, roomId: Int, memoId: Int, listIdx: Int) {
        if(flag){
            MemoService(this).tryDeleteMemo(roomId, memoId)
            toBeremoved = listIdx
        }
    }


    override fun onPostMemoSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                MemoService(this).tryGetMemo(ApplicationClass.sSharedPreferences.getInt("roomId", 0))
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostMemoFailure(message: String) {
        showCustomToast(message)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onGetMemoSuccess(response: GetMemoResponse) {
        when(response.code){
            200 -> {
                memoList = response.result.memo

                binding.memoFrameLayout.removeAllViews()

                if(memoList.size > 0){
                    binding.memoEmptyImg.visibility = View.GONE
                }else{
                    binding.memoEmptyImg.visibility = View.VISIBLE
                }

                for(i in memoList.indices){
                    var item = activity?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                    var target = item.inflate(R.layout.memo_item, null)
                    target.memo_item_tv.text = memoList[i].memo
                    target.memo_item_timestamp_tv.text = memoList[i].createdAt

                    target.memo_item_layout.setBackgroundColor(Color.parseColor(memoList[i].memoColor))

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

                                val request = PatchMemoRequest(v.x, v.y)
                                MemoService(this).tryPatchMemo(ApplicationClass.sSharedPreferences.getInt("roomId",-1), memoList[i].memoId, request)
                            }
                        }
                        true
                    }

                    target.memo_item_more_btn.setOnClickListener {
                        Log.d("로그", "hahaha")
                        val deleteDialog = MemoDeleteDialog(requireContext(), this, ApplicationClass.sSharedPreferences.getInt("roomId", -1), memoList[i].memoId, i)
                        deleteDialog.show(childFragmentManager, deleteDialog.tag)
                        true
                    }

                    var frame = binding.memoFrameLayout
                    var params = FrameLayout.LayoutParams((size!!.x * 0.4).toInt(),
                        ViewGroup.LayoutParams.WRAP_CONTENT
                    )

                    params.leftMargin = memoList[i].x.toInt()
                    params.topMargin = memoList[i].y.toInt()

                    frame.addView(target, params)

                    idx++
                }

            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onGetMemoFailure(message: String) {
        showCustomToast(message)
    }

    override fun onDeleteMemoSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                MemoService(this).tryGetMemo(ApplicationClass.sSharedPreferences.getInt("roomId", -1))
                showCustomToast(response.message.toString())
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onDeleteMemoFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPatchMemoSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {

            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPatchMemoFailure(message: String) {
        showCustomToast(message)
    }
}