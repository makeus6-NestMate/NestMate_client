package com.nestmate.nm1.src.main.home.nest.memo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.FrameLayout
import com.bumptech.glide.Glide
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseFragment
import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.databinding.FragmentMemoBinding
import com.nestmate.nm1.src.main.home.nest.memo.model.*
import kotlinx.android.synthetic.main.memo_item.view.*

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
            val customDialog = MemoAddDialog(memoCustomDialogInterface = this)
            customDialog.show(childFragmentManager, "test")
        }
    }

    override fun onResume() {
        super.onResume()
        MemoService(this).tryGetMemo(ApplicationClass.sSharedPreferences.getInt("roomId", -1))
        showLoadingDialog(requireContext())
    }


    @SuppressLint("ClickableViewAccessibility", "ResourceAsColor")
    override fun onConfirmBtnClicked(isEdit: Boolean, memoId: Int?, message: String, color: String?) {
        Log.d("로그", "This is MemoFragment${color}")
        if(!isEdit){
            var item = activity?.applicationContext?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            var target = item.inflate(R.layout.memo_item, null)
            target.memo_item_tv.text = message

            if(color != null){
                target.memo_item_layout.setBackgroundColor(Color.parseColor(color))
            }
            val request = PostMemoRequest(message, 0f, 0f, (color ?: "#ff9e81"))
            MemoService(this@MemoFragment).tryPostMemo(ApplicationClass.sSharedPreferences.getInt("roomId", 0), request)
            showLoadingDialog(requireContext())
        }else{
            val request = PutMemoRequest(message, color!!)
            MemoService(this@MemoFragment).tryPutMemo(ApplicationClass.sSharedPreferences.getInt("roomId", 0), memoId!!, request)
            showLoadingDialog(requireContext())
        }

    }

    override fun onCancelBtnClicked() {

    }

    override fun onDeleteClicked(flag: Boolean, roomId: Int, memoId: Int, listIdx: Int) {
        if(flag){
            MemoService(this).tryDeleteMemo(roomId, memoId)
            toBeremoved = listIdx
        }
    }

    override fun onEditClicked(flag: Boolean, roomId: Int, memoId: Int, listIdx: Int) {
        val customDialog = MemoAddDialog(isEdit = true, roomId = roomId, memoId = memoId, color = memoList[listIdx].memoColor,
            content = memoList[listIdx].memo, memoCustomDialogInterface = this)
        customDialog.show(childFragmentManager, "test")
    }


    override fun onPostMemoSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                MemoService(this).tryGetMemo(ApplicationClass.sSharedPreferences.getInt("roomId", 0))
                showLoadingDialog(requireContext())
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostMemoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onGetMemoSuccess(response: GetMemoResponse) {
        dismissLoadingDialog()
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
                    Glide.with(this).load(memoList[i].profileImg).error(R.drawable.chicken_img).into(target.memo_item_profile_img)

                    val time = memoList[i].createdAt
                    var isAm = "오전"
                    var hours = "0"
                    if((time.substring(6,8).toInt()) > 12){
                        isAm = "오후"
                        hours = (time.substring(6,8).toInt() - 12).toString()
                    }else{
                        isAm = "오전"
                        hours = time.substring(6,8)
                    }

                    target.memo_item_timestamp_tv.text =  time.substring(0, 2) + "월 " + time.substring(3,5) + "일 " + isAm + " " + hours + "시 " + time.substring(9,11)+ "분"

                    if(memoList[i].isOwner == "Y"){
                        target.memo_item_more_btn.visibility = View.VISIBLE
                    }else{
                        target.memo_item_more_btn.visibility = View.GONE
                    }

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
                                showLoadingDialog(requireContext())
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
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteMemoSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                MemoService(this).tryGetMemo(ApplicationClass.sSharedPreferences.getInt("roomId", -1))
                showLoadingDialog(requireContext())
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onDeleteMemoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPatchMemoSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {

            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPatchMemoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPutMemoSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                MemoService(this).tryGetMemo(ApplicationClass.sSharedPreferences.getInt("roomId", -1))
                showLoadingDialog(requireContext())
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPutMemoFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}