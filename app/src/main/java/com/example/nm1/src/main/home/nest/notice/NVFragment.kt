package com.example.nm1.src.main.home.nest.notice

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.config.BaseResponse
import com.example.nm1.databinding.FragmentNvBinding
import com.example.nm1.src.main.home.nest.notice.model.GetNoticeVoteResponse
import com.example.nm1.src.main.home.nest.notice.model.NoticeVoteInfo
import com.example.nm1.src.main.home.nest.notice.model.PostNoticeRequest
import com.example.nm1.src.main.home.nest.notice.model.PostVoteRequest
import com.example.nm1.src.main.home.nest.notice.vote.VoteActivity
import com.example.nm1.src.main.home.nest.rule.RuleRVAdapter

class NVFragment : BaseFragment<FragmentNvBinding>(FragmentNvBinding::bind, R.layout.fragment_nv), NoticeVoteView, NVDialogInterface {
    private var dataList = ArrayList<NoticeVoteInfo>()
    private lateinit var adapter: NoticeVoteRVAdapter
    private var roomId = ApplicationClass.sSharedPreferences.getInt("roomId", -1)
    private var page = 0

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
        this.adapter.setOnClickListener(onClicked)
        binding.noticeRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.noticeRecyclerview.adapter = this.adapter
    }

    override fun onResume() {
        super.onResume()
        NoticeVoteService(this).tryGetNoticeVote(roomId, page)
        if(dataList.size > 0){
            binding.noticeEmptyImg.visibility = View.GONE
        }else{
            binding.noticeEmptyImg.visibility = View.VISIBLE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                val from = data?.getBooleanExtra("isNotice", true)
                if(from == true){
                    val item = data?.getSerializableExtra("data") as NoticeVoteData
                    val request = PostNoticeRequest(item.content!!)
                    NoticeVoteService(this).tryPostNotice(roomId, request)
                }else{
                    val item = data?.getSerializableExtra("data") as NoticeVoteData
                    val request = PostVoteRequest(item.title!!, item.choiceArr!!)
                    NoticeVoteService(this).tryPostVote(roomId, request)
                }

            }
        }
    }

    override fun onPostNoticeSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostNoticeFailure(message: String) {
        showCustomToast(message)
    }

    override fun onGetNoticeVoteSuccess(response: GetNoticeVoteResponse) {
        when(response.code){
            200 -> {
                dataList.clear()
                val result = response.result
                for(data in result.noticeVote){
                    if(data.isNotice == "Y"){
                        val temp = NoticeVoteInfo(profileImg = data.profileImg, noticeId = data.noticeId, notice = data.notice, createdAt = data.createdAt, isNotice = data.isNotice)
                        dataList.add(temp)

                    }else{
                        val temp = NoticeVoteInfo(profileImg = data.profileImg, voteId = data.voteId, title = data.title, createdAt = data.createdAt, isFinished = data.isFinished, isNotice = data.isNotice)
                        dataList.add(temp)
                    }
                }

                if(dataList.size > 0){
                    binding.noticeEmptyImg.visibility = View.GONE
                }else{
                    binding.noticeEmptyImg.visibility = View.VISIBLE
                }

                adapter.notifyDataSetChanged()
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onGetNoticeVoteFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPostVoteSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostVoteFailure(message: String) {
        showCustomToast(message)
    }

    override fun onDeleteNoticeSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onDeleteNoticeFailure(message: String) {
        showCustomToast(message)
    }

    override fun onDeleteVoteSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onDeleteVoteFailure(message: String) {
        showCustomToast(message)
    }

    private val onClicked = object: NoticeVoteRVAdapter.OnItemClickListener{
        override fun onNoticeClicked(position: Int, noticeId: Int) {
            val dialog = NoticeVoteDialog(isNotice = true, position = position, noticeId = noticeId, nvDialogInterface = this@NVFragment)
            dialog.show(childFragmentManager, "notice")
        }

        override fun onVoteMoreClicked(position: Int, voteId: Int) {
            TODO("Not yet implemented")
        }

        override fun onVoteClicked(position: Int, voteId: Int) {
            val intent = Intent(activity, VoteActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("roomId", roomId)
            intent.putExtra("voteId", voteId)
            startActivity(intent)

        }


    }

    override fun onEditClicked() {
    }

    override fun onDeleteClicked(isNotice: Boolean, position: Int, noticeId: Int?, voteId: Int?) {
        if(isNotice){
            dataList.removeAt(position)
            adapter.notifyItemRemoved(position)
            NoticeVoteService(this@NVFragment).tryDeleteNotice(roomId, noticeId!!)
        }else{
            dataList.removeAt(position)
            adapter.notifyItemRemoved(position)
            NoticeVoteService(this@NVFragment).tryDeleteVote(roomId, voteId!!)
        }

    }


}