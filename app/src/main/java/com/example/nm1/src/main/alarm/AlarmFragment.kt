package com.example.nm1.src.main.alarm

import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentAlarmBinding
import com.example.nm1.src.main.alarm.model.AlarmInfo
import com.example.nm1.src.main.alarm.model.GetAlarmResponse
import kotlinx.android.synthetic.main.toolbar_back.*


class AlarmFragment : BaseFragment<FragmentAlarmBinding>(
    FragmentAlarmBinding::bind,
    R.layout.fragment_alarm
), AlarmFragmentView {

    private lateinit var alarmList: List<AlarmInfo>
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private var paging : Int = 0
    private var alarmListAdapter : AlarmListAdapter? = null

    private var sumList = mutableListOf<AlarmInfo>()

    var isAlarmed = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.alarmToolbar.toolbarTitle.text = "알림"

        binding.alarmList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 오른쪽 스크롤, 음수일경우엔 왼쪽 스크롤
//                수평으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.alarmList.canScrollVertically(1)){
                    if (!isAlarmed) {
                        dismissLoadingDialog()
                        showLoadingDialog(requireContext())
                        AlarmService(this@AlarmFragment).tryGetAlarm(roomId, ++paging)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        paging = 0
        sumList.clear()
        isAlarmed = false
        showLoadingDialog(requireContext())
        AlarmService(this).tryGetAlarm(roomId, paging)
    }


    override fun onGetAlarmSuccess(response: GetAlarmResponse) {
        dismissLoadingDialog()
        alarmList = response.result.alarmInfo
        //      맨 처음(page=0) 둥지가 없으면
        if (paging==0 && alarmList.isNullOrEmpty()){

        }
//      맨 처음(page=0) -> 둥지가 하나라도 있으면
        else if (paging==0 && alarmList.isNotEmpty()){
            Log.d("둥지", "둥지있음")
            sumList.addAll(alarmList)
            alarmListAdapter = AlarmListAdapter(requireContext(), sumList)
            binding.alarmList.adapter = alarmListAdapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (paging!=0 && alarmList.isNotEmpty()){
            Log.d("둥지", "둥지추가")
            sumList.addAll(alarmList)
            alarmListAdapter!!.notifyItemInserted(sumList.size-1)
        }
//        페이지추가 끝
        if (paging!=0 && alarmList.isNullOrEmpty()){
            isAlarmed = true
        }
    }

    override fun onGetAlarmFailure(message: String) {
        showCustomToast("다시 시도 해주세요.")
    }
}