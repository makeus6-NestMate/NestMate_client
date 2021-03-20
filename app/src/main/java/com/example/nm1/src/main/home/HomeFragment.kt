package com.example.nm1.src.main.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentHomeBinding
import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.NestInfo
import com.example.nm1.src.main.home.nest.NestActivity

class HomeFragment : BaseFragment<FragmentHomeBinding>(FragmentHomeBinding::bind,R.layout.fragment_home), HomeFragmentView {
    private var nestlist:List<NestInfo>?=null
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
        nestlist = response.result.roomInfo

//      둥지가 없으면
        if (nestlist!!.isEmpty()){
            binding.homeTvOwnerEmpty.text = "'"+response.result.userName+"'"
            binding.homeLayoutUser.visibility = View.INVISIBLE
            binding.homeDrawableBird.visibility = View.INVISIBLE
            binding.nestList.visibility = View.INVISIBLE
            binding.homeLayoutUserEmpty.visibility = View.VISIBLE
            binding.homeLayoutEmpty.visibility = View.VISIBLE
        }
//      둥지가 하나라도 있으면
        else{
            binding.homeTvOwner.text = "'"+response.result.userName+"'"
            binding.homeLayoutUser.visibility = View.VISIBLE
            binding.homeDrawableBird.visibility = View.VISIBLE
            binding.homeLayoutUserEmpty.visibility = View.INVISIBLE
            binding.nestList.visibility = View.VISIBLE
            binding.homeLayoutEmpty.visibility = View.INVISIBLE

            adapter = HomeNestAdapter(requireContext(), nestlist!!)
        }
        binding.nestList.adapter = adapter
    }

    override fun onGetNestFailure(message: String) {
        dismissLoadingDialog()
        showCustomToast(message)
    }
}