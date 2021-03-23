package com.example.nm1.src.main.home.nest.notice

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentNvBinding

class NVFragment  : BaseFragment<FragmentNvBinding>(FragmentNvBinding::bind, R.layout.fragment_nv) {
    private var dataList = ArrayList<NoticeVoteData>()
    private lateinit var adapter: NoticeVoteRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(dataList.size == 0){
            binding.noticeEmptyImg.visibility = View.VISIBLE
        }else{
            binding.noticeEmptyImg.visibility = View.GONE
        }

        binding.noticeVoteAddBtn.setOnClickListener {
            val intent = Intent(activity, NoticeVoteActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivityForResult(intent, 100)
        }

        this.adapter = NoticeVoteRVAdapter()
        this.adapter.submitList(this.dataList)
        binding.noticeRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.noticeRecyclerview.adapter = this.adapter
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                val data = data?.getSerializableExtra("data") as NoticeVoteData
                dataList.add(data)
                adapter.notifyDataSetChanged()
            }
        }
    }


}