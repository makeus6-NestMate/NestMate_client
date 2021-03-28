package com.example.nm1.src.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentHomeBinding
import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.NestInfo
import com.example.nm1.src.main.home.model.PutEditNestResponse
import com.example.nm1.src.main.home.nest.NestActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(
    FragmentHomeBinding::bind,
    R.layout.fragment_home
), HomeView {
    private var nestlist = mutableListOf<NestInfo>()
    var adapter:HomeNestAdapter?=null

    private var page = 0       // 현재 페이지
    var isnestend = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.homeLayoutEmpty.setOnClickListener {
            val intent = Intent(activity, NestActivity::class.java)
            startActivity(intent)
        }

        val addnestedialog = HomeAddNestDialog()
        binding.homeBtnAddnest.setOnClickListener {
            addnestedialog.show(parentFragmentManager, "addnestdialog")
        }

//        둥지 추가 후 바로 반영
        setFragmentResultListener("addnest") { _, bundle ->
            bundle.getString("addnest_ok")?.let {
                if (it == "ok") {
                    page = 0
                    showLoadingDialog(requireContext())
                    HomeService(this).tryGetNest(page)
                }
            }
        }

//        둥지 수정 후 바로 반영
        setFragmentResultListener("editnest") { _, bundle ->
            bundle.getString("editnest_ok")?.let {
                if (it == "ok") {
                    page = 0
                    showLoadingDialog(requireContext())
                    HomeService(this).tryGetNest(page)
                }
            }
        }

        binding.nestList.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )

        binding.nestList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

//             direction:  양수일경우엔 오른쪽 스크롤, 음수일경우엔 왼쪽 스크롤
//                수평으로 더이상 스크롤이 안되면, 데이터를 더해서 불러옴
                if (!binding.nestList.canScrollHorizontally(1)){
                    if (!isnestend) {
                        dismissLoadingDialog()
                        showLoadingDialog(requireContext())
                        HomeService(this@HomeFragment).tryGetNest(++page)
                    }
                }
            }
        })
    }

    override fun onResume() {
        super.onResume()
        page = 0
        nestlist.clear()
        showLoadingDialog(requireContext())
        HomeService(this).tryGetNest(page)
    }

    override fun onAddNestSuccess(response: AddNestResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddNestFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetNestSuccess(response: GetNestResponse) {
        dismissLoadingDialog()

//      맨 처음(page=0) 둥지가 없으면
        if (page==0 && response.result.roomInfo.isNullOrEmpty()){
            Log.d("둥지", "둥지없음")
            binding.homeTvOwnerEmpty.text = "'"+response.result.userName+"'"
            binding.homeLayoutUser.visibility = View.INVISIBLE
            binding.homeDrawableBird.visibility = View.INVISIBLE
            binding.nestList.visibility = View.INVISIBLE
            binding.homeLayoutUserEmpty.visibility = View.VISIBLE
            binding.homeLayoutEmpty.visibility = View.VISIBLE
        }
//      맨 처음(page=0) -> 둥지가 하나라도 있으면
        else if (page==0 && response.result.roomInfo.isNotEmpty()){
            Log.d("둥지", "둥지있음")
            binding.homeTvOwner.text = "'"+response.result.userName+"'"
            binding.homeLayoutUser.visibility = View.VISIBLE
            binding.homeDrawableBird.visibility = View.VISIBLE
            binding.homeLayoutUserEmpty.visibility = View.INVISIBLE
            binding.nestList.visibility = View.VISIBLE
            binding.homeLayoutEmpty.visibility = View.INVISIBLE

            nestlist.addAll(response.result.roomInfo)
            adapter = HomeNestAdapter(requireContext(), nestlist, parentFragmentManager)
            binding.nestList.adapter = adapter
        }
//      page=1부터 불러오고, 둥지가 있으면 추가해줘야함 ->
        else if (page!=0 && response.result.roomInfo.isNotEmpty()){
            Log.d("둥지", "둥지추가")
            nestlist.addAll(response.result.roomInfo)
            adapter!!.notifyItemInserted(nestlist.size-1)
        }

//        페이지추가 끝
        if (page!=0 && response.result.roomInfo.isNullOrEmpty()){
            Log.d("둥지", "둥지끝")
            isnestend = true
        }
    }

    override fun onGetNestFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }

    override fun onPutNestSuccess(response: PutEditNestResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutNestFailure(message: String) {
        TODO("Not yet implemented")
    }
}