package com.nestmate.nm1.src.main.home.nest.notice

import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.config.BaseFragment
import com.nestmate.nm1.config.BaseResponse
import com.nestmate.nm1.databinding.FragmentNvBinding
import com.nestmate.nm1.src.main.home.nest.notice.model.GetNoticeVoteResponse
import com.nestmate.nm1.src.main.home.nest.notice.model.NoticeVoteInfo
import com.nestmate.nm1.src.main.home.nest.notice.model.PostNoticeRequest
import com.nestmate.nm1.src.main.home.nest.notice.model.PostVoteRequest
import com.nestmate.nm1.src.main.home.nest.notice.vote.VoteActivity

class NVFragment : BaseFragment<FragmentNvBinding>(FragmentNvBinding::bind, R.layout.fragment_nv), NoticeVoteView, NVDialogInterface {
    private var dataList = ArrayList<NoticeVoteInfo>()
    private lateinit var adapter: NoticeVoteRVAdapter
    private var roomId = ApplicationClass.sSharedPreferences.getInt("roomId", -1)
    private var page = 0
    private lateinit var thisContext: Context
    var isEnd = false
    private var noticeIdSet = HashSet<Int>()
    private var voteIdSet = HashSet<Int>()
    private var lastPage = 0
    private var editPosition = -1
    private var editContent = ""
    private lateinit var editData: NoticeVoteInfo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(dataList.size == 0){
            binding.noticeEmptyImg.visibility = View.VISIBLE
        }else{
            binding.noticeEmptyImg.visibility = View.GONE
        }
        thisContext = requireContext()

        binding.noticeVoteAddBtn.setOnClickListener {
            val intent = Intent(activity, NoticeVoteActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivityForResult(intent, 100)
        }



        binding.noticeRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 오른쪽 스크롤, 음수일경우엔 왼쪽 스크롤
//                수평으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.noticeRecyclerview.canScrollHorizontally(1)){
                    if (!isEnd) {
                        lastPage = page
                        dismissLoadingDialog()
                        showLoadingDialog(requireContext())
                        NoticeVoteService(this@NVFragment).tryGetNoticeVote(roomId, ++page)
                    }
                }
            }
        })

//        binding.noticeRecyclerview.addOnScrollListener(object: RecyclerView.OnScrollListener(){
//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//
//                val lastVisibleItemPosition = (binding.noticeRecyclerview.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
//                val itemTotalCount = binding.noticeRecyclerview.adapter!!.itemCount - 1
//
//                if(lastVisibleItemPosition == itemTotalCount){
//                    showLoadingDialog(requireContext())
//                    NoticeVoteService(this@NVFragment).tryGetNoticeVote(roomId, ++page)
//                }
//            }
//        })

        this.adapter = NoticeVoteRVAdapter()
        this.adapter.submitList(this.dataList)
        this.adapter.setOnClickListener(onClicked)
        binding.noticeRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.noticeRecyclerview.adapter = this.adapter
    }

    override fun onResume() {
        super.onResume()
        page = 0
        isEnd = false
        dataList.clear()
        noticeIdSet.clear()
        voteIdSet.clear()
        showLoadingDialog(requireContext())
        NoticeVoteService(this).tryGetNoticeVote(roomId, page)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100){
            if(resultCode == RESULT_OK){
                val from = data?.getBooleanExtra("isNotice", true)
                if(from == true){
                    val item = data?.getSerializableExtra("data") as NoticeVoteData
                    val request = PostNoticeRequest(item.content!!)
                    //showLoadingDialog(thisContext)
                    NoticeVoteService(this@NVFragment).tryPostNotice(roomId, request)

                }else{
                    val item = data?.getSerializableExtra("data") as NoticeVoteData
                    val request = PostVoteRequest(item.title!!, item.choiceArr!!)
                    //showLoadingDialog(thisContext)
                    NoticeVoteService(this@NVFragment).tryPostVote(roomId, request)

                }

            }
        }
        else if(requestCode == 200) {
            if (resultCode == RESULT_OK) {
                val from = data?.getBooleanExtra("isNotice", true)
                if (from == true) {
                    val item = data?.getSerializableExtra("data") as NoticeVoteData
                    editContent = item.content!!
                    val request = PostNoticeRequest(item.content!!)
                    //showLoadingDialog(thisContext)
                    NoticeVoteService(this@NVFragment).tryPutNotice(
                        roomId,
                        data.getIntExtra("noticeId", -1),
                        request
                    )
                }
            }
        }

    }

    override fun onPostNoticeSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                page = 0
                isEnd = false
                dataList.clear()
                noticeIdSet.clear()
                voteIdSet.clear()
                showLoadingDialog(requireContext())
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostNoticeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onGetNoticeVoteSuccess(response: GetNoticeVoteResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {

                val result = response.result

                if(page == 0 && result.noticeVote.size != 0){
                    for(data in result.noticeVote){
                        if(data.isNotice == "Y"){
                            if(!noticeIdSet.contains(data.noticeId)){
                                val temp = NoticeVoteInfo(profileImg = data.profileImg, noticeId = data.noticeId, notice = data.notice, createdAt = data.createdAt, isNotice = data.isNotice, isOwner = data.isOwner)
                                dataList.add(temp)
                                noticeIdSet.add(data.noticeId!!)
                            }
                        }else{
                            if(!voteIdSet.contains(data.voteId)){
                                val temp = NoticeVoteInfo(profileImg = data.profileImg, voteId = data.voteId, title = data.title, createdAt = data.createdAt, isFinished = data.isFinished, isNotice = data.isNotice, isOwner = data.isOwner)
                                dataList.add(temp)
                                voteIdSet.add(data.voteId!!)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()
                }
                else if(page != 0 && result.noticeVote.size != 0){
                    for(data in result.noticeVote){
                        if(data.isNotice == "Y"){
                            if(!noticeIdSet.contains(data.noticeId)){
                                val temp = NoticeVoteInfo(profileImg = data.profileImg, noticeId = data.noticeId, notice = data.notice, createdAt = data.createdAt, isNotice = data.isNotice, isOwner = data.isOwner)
                                dataList.add(temp)
                                noticeIdSet.add(data.noticeId!!)
                            }
                        }else{
                            if(!voteIdSet.contains(data.voteId)){
                                val temp = NoticeVoteInfo(profileImg = data.profileImg, voteId = data.voteId, title = data.title, createdAt = data.createdAt, isFinished = data.isFinished, isNotice = data.isNotice, isOwner = data.isOwner)
                                dataList.add(temp)
                                voteIdSet.add(data.voteId!!)
                            }
                        }
                    }

                    adapter.notifyDataSetChanged()
                }

                if(page != 0 && result.noticeVote.size == 0){
                    isEnd = true
                    page = 0
                }

                Log.d("okhttp", "onGetSuccess: isEnd: ${isEnd} / page: ${page} / lastPage = ${lastPage}")

                if(dataList.size > 0){
                    binding.noticeEmptyImg.visibility = View.GONE
                }else{
                    binding.noticeEmptyImg.visibility = View.VISIBLE
                }
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onGetNoticeVoteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPostVoteSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                page = 0
                isEnd = false
                dataList.clear()
                noticeIdSet.clear()
                voteIdSet.clear()
                showLoadingDialog(requireContext())
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostVoteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteNoticeSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                page = 0
                isEnd = false
                dataList.clear()
                noticeIdSet.clear()
                voteIdSet.clear()
                showLoadingDialog(requireContext())
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onDeleteNoticeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onDeleteVoteSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                page = 0
                isEnd = false
                dataList.clear()
                noticeIdSet.clear()
                voteIdSet.clear()
                showLoadingDialog(requireContext())
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onDeleteVoteFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPutNoticeSuccess(response: BaseResponse) {
        dismissLoadingDialog()
        when(response.code){
            200 -> {
                page = 0
                isEnd = false
                dataList.clear()
                noticeIdSet.clear()
                voteIdSet.clear()
                showLoadingDialog(requireContext())
                NoticeVoteService(this).tryGetNoticeVote(roomId, page)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPutNoticeFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    private val onClicked = object: NoticeVoteRVAdapter.OnItemClickListener{
        override fun onNoticeClicked(position: Int, noticeId: Int) {
            val dialog = NoticeVoteDialog(isNotice = true, position = position, noticeId = noticeId, nvDialogInterface = this@NVFragment)
            dialog.show(childFragmentManager, "notice")
        }

        override fun onVoteMoreClicked(position: Int, voteId: Int) {
            val dialog = NoticeVoteDialog(isNotice = false, position = position, voteId = voteId, nvDialogInterface = this@NVFragment)
            dialog.show(childFragmentManager, "vote")
        }

        override fun onVoteClicked(position: Int, voteId: Int) {
            val intent = Intent(activity, VoteActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("roomId", roomId)
            intent.putExtra("voteId", voteId)
            startActivity(intent)

        }


    }

    override fun onEditClicked(isNotice: Boolean, isEdit: Boolean, position: Int, noticeId: Int) {
        if(isNotice && isEdit){
            editPosition = position
            editData = dataList[position]
            val intent = Intent(activity, NoticeVoteActivity::class.java)
            intent.putExtra("position", position)
            intent.putExtra("roomId", roomId)
            intent.putExtra("noticeId", noticeId)
            intent.putExtra("isEdit", true)
            intent.putExtra("content", dataList[position].notice)
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
            startActivityForResult(intent, 200)
        }
    }

    override fun onDeleteClicked(isNotice: Boolean, position: Int, noticeId: Int?, voteId: Int?) {
        if(isNotice){
            dataList.removeAt(position)
            adapter.notifyItemRemoved(position)
            showLoadingDialog(thisContext)
            NoticeVoteService(this@NVFragment).tryDeleteNotice(roomId, noticeId!!)

        }else{
            dataList.removeAt(position)
            adapter.notifyItemRemoved(position)
            showLoadingDialog(thisContext)
            NoticeVoteService(this@NVFragment).tryDeleteVote(roomId, voteId!!)

        }

    }


}