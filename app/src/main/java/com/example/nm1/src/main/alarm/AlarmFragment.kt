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
    private var sumList = mutableListOf<AlarmInfo>()
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    private var paging : Int = -1
    private lateinit var alarmListAdapter : AlarmListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.alarmToolbar.toolbarTitle.text = "알림"

        alarmListAdapter = AlarmListAdapter(requireContext(), sumList)
        binding.alarmList.adapter=alarmListAdapter
        AlarmService(this).tryGetAlarm(roomId, ++paging)
        binding.alarmList.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition =
                    (recyclerView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                val itemTotalCount = recyclerView.adapter?.itemCount
                if (lastVisibleItemPosition + 1 == itemTotalCount) {
                    AlarmService(this@AlarmFragment).tryGetAlarm(roomId, ++paging)
                }
            }
        })
    }

    override fun onGetAlarmSuccess(response: GetAlarmResponse) {
        alarmList = response.result.alarmInfo
        for(idx in alarmList.indices) sumList.add(alarmList[idx])

        binding.alarmList.adapter?.notifyItemInserted(sumList.size-1)
    }

    override fun onGetAlarmFailure(message: String) {
        showCustomToast("다시 시도 해주세요.")
    }
}