package com.example.nm1.src.main.home.nest.notice.vote

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.config.BaseResponse
import com.example.nm1.databinding.FragmentVoteBinding
import com.example.nm1.src.main.home.nest.notice.vote.model.Choice
import com.example.nm1.src.main.home.nest.notice.vote.model.GetVoteResponse
import com.example.nm1.src.main.home.nest.notice.vote.model.PatchVoteRequest
import com.example.nm1.src.main.home.nest.notice.vote.voteMember.VoteMemberFragment

class VoteFragment: BaseFragment<FragmentVoteBinding>(FragmentVoteBinding::bind, R.layout.fragment_vote), VoteFragmentView {

    private var roomId: Int? = null
    private var voteId: Int? = null

    private var dataList = ArrayList<Choice>()
    private lateinit var adapter: VoteDetailRVAdapter
    private var selectedId = -1
    private var confirmedId = -1
    private var isFinished = "N"



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var extra = this.arguments
        if(extra != null){
            extra = arguments
            roomId = extra!!.getInt("roomId")
            voteId = extra!!.getInt("voteId")

        }

        adapter = VoteDetailRVAdapter()
        adapter.submitList(dataList)
        binding.voteRecyclerview.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.voteRecyclerview.adapter = adapter

        adapter.setOnClickListener(onClicked)

        showLoadingDialog(requireContext())
        VoteFragmentService(this).tryGetVote(roomId!!, voteId!!)


        binding.voteConfirmBtn.setOnClickListener {
            if(selectedId != -1){
                val request = PatchVoteRequest(selectedId)
                VoteFragmentService(this).tryPatchVote(roomId!!, voteId!!, request)
                showLoadingDialog(requireContext())
            }
        }

        binding.voteFinishBtn.setOnClickListener {
            VoteFragmentService(this).tryPatchVoteFinish(roomId!!, voteId!!)
        }
    }


    override fun onGetVoteSuccess(response: GetVoteResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                val result = response.result
                this.isFinished = result.isFinished

                if(result.isFinished == "N"){
                    binding.voteIng.visibility = View.VISIBLE
                    binding.voteDone.visibility = View.GONE
                }else{
                    binding.voteIng.visibility = View.GONE
                    binding.voteDone.visibility = View.VISIBLE
                }
                Log.d("okhttp", result.unVoteMemeberCnt.toString())
                binding.voteMemberCnt.text = "미참여 " +result.unVoteMemeberCnt.toString() + "명"

                if(result.choiceId == -1 && result.isFinished == "N"){
                    binding.voteConfirmBtn.visibility = View.VISIBLE
                    binding.voteTitle.text = result.voteTitle

                    binding.voteConfirmBtn.visibility = View.VISIBLE
                    dataList.clear()
                    //adapter.updateStatus(result.isFinished)
                    adapter.isFinished = "N"
                    dataList.addAll(result.choice as ArrayList<Choice>)
                    adapter.notifyDataSetChanged()

                }else{
                    binding.voteTitle.text = result.voteTitle
                    binding.voteConfirmBtn.visibility = View.GONE
                    dataList.clear()
                    adapter.isFinished = "Y"
                    //adapter.updateStatus(result.isFinished)
                    dataList.addAll(result.choice as ArrayList<Choice>)
                    adapter.notifyDataSetChanged()
                }

                if(result.choiceId != -1){
                    adapter.userChoice = result.choiceId
                    confirmedId = result.choiceId
                }

                if(result.choiceId != -1 && result.isOwner){
                    binding.voteConfirmBtn.visibility = View.GONE
                    binding.voteFinishBtn.visibility = View.VISIBLE
                }

                if(isFinished == "Y"){
                    binding.voteConfirmBtn.visibility = View.GONE
                    binding.voteFinishBtn.visibility = View.GONE
                }
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onGetVoteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPatchVoteSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                VoteFragmentService(this).tryGetVote(roomId!!, voteId!!)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPatchVoteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPatchVoteFinishSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                VoteFragmentService(this).tryGetVote(roomId!!, voteId!!)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPatchVoteFinishFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    private val onClicked = object: VoteDetailRVAdapter.OnItemClickListener{
        override fun onRadioClicked(choiceId: Int) {
            selectedId = choiceId
            binding.voteConfirmBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
        }

        override fun onMemberClicked(choiceId: Int) {
            if(isFinished == "Y" || confirmedId != -1){
                val fragment = VoteMemberFragment()
                val bundle = Bundle()
                bundle.putInt("roomId", roomId!!)
                bundle.putInt("voteId", voteId!!)
                bundle.putInt("choiceId", choiceId)
                fragment.arguments = bundle
                (activity as VoteActivity).replaceFragment(fragment)
            }
        }

    }
}