package com.example.nm1.src.main.home.nest.notice.vote.voteMember

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentVoteMemberBinding
import com.example.nm1.src.main.home.nest.notice.vote.VoteActivity
import com.example.nm1.src.main.home.nest.notice.vote.voteMember.model.GetVoteMemberResponse
import com.example.nm1.src.main.home.nest.notice.vote.voteMember.model.VoteMember

class VoteMemberFragment: BaseFragment<FragmentVoteMemberBinding>(FragmentVoteMemberBinding::bind, R.layout.fragment_vote_member), VoteMemberView{
    private var roomId: Int? = null
    private var voteId: Int? = null
    private var choiceId: Int? = null
    private var dataList = ArrayList<VoteMember>()
    private lateinit var adapter: VoteMemberRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var extra = this.arguments
        if(extra != null){
            extra = arguments
            roomId = extra!!.getInt("roomId")
            voteId = extra!!.getInt("voteId")
            choiceId = extra!!.getInt("choiceId")
        }

        adapter = VoteMemberRVAdapter()
        adapter.submitList(dataList)
        binding.voteMemberRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.voteMemberRecyclerview.adapter = adapter

        VoteMemberService(this).tryGetVoteMember(roomId!!, voteId!!, choiceId!!)
        showLoadingDialog(requireContext())

    }

    override fun onGetVoteMemberSuccess(response: GetVoteMemberResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                val result = response.result
                dataList.clear()
                dataList.addAll(result.voteMember)
                adapter.notifyDataSetChanged()

            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onGetVoteMemberFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }


}