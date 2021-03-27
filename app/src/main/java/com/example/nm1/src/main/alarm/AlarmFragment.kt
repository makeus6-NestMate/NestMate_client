package com.example.nm1.src.main.alarm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.alarmToolbar.toolbarTitle.text = "알림"

        AlarmService(this).tryGetAlarm(roomId)
    }

    override fun onGetAlarmSuccess(response: GetAlarmResponse) {
        alarmList = response.result.alarmInfo
        val alarmListAdapter = AlarmListAdapter(requireContext(), alarmList)
        // 이게 맞나...? 스와이프 삭제...?
        binding.alarmList.adapter = alarmListAdapter
//        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
//        itemTouchHelper.attachToRecyclerView(binding.alarmList)
    }

    override fun onGetAlarmFailure(message: String) {
        TODO("Not yet implemented")
    }

    private var itemTouchCallback: ItemTouchHelper.SimpleCallback =
        object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, swipeDir: Int) {
                // 삭제되는 아이템의 포지션을 가져온다
                val position = viewHolder.adapterPosition
                // 아답타에게 알린다
                binding.alarmList.adapter?.notifyItemRemoved(position)
            }
        }
}