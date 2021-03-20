package com.example.nm1.src.main.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentHomeBinding
import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.NestInfo
import com.example.nm1.src.main.home.nest.NestActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind,R.layout.fragment_home), HomeFragmentView {
    var nestlist:List<NestInfo>?=null
    var adapter:HomeNestAdapter?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showLoadingDialog(requireContext())
        HomeService(this).tryGetNest()

        binding.homeLayoutEmpty.setOnClickListener {
            val intent = Intent(activity, NestActivity::class.java)
            startActivity(intent)
        }

        val addnestedialog = HomeAddNestDialogFragment()
        binding.homeBtnAddnest.setOnClickListener {
            addnestedialog.show(parentFragmentManager, "addnestdialog")
        }

        setFragmentResultListener("addnest") { _, bundle ->
            bundle.getString("addnest_ok")?.let {
                if (it=="ok"){
                    showLoadingDialog(requireContext())
                    HomeService(this).tryGetNest()
                }
            }
        }
        binding.nestList.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
    }

    override fun onAddNestSuccess(response: AddNestResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddNestFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetNestSuccess(response: GetNestResponse) {
        dismissLoadingDialog()
        binding.homeTvOwner.text = "'"+response.result.userName+"'님의 둥지"
        nestlist = response.result.roomInfo
        if (nestlist!!.isNotEmpty()){
            binding.homeLayoutEmpty.visibility = View.INVISIBLE //빌때의 안내 둥지 안보이게
            binding.nestList.visibility = View.VISIBLE //둥지 리스트 보이게
            adapter = HomeNestAdapter(requireContext(), nestlist!!)
        }
        binding.nestList.adapter = adapter
    }

    override fun onGetNestFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}