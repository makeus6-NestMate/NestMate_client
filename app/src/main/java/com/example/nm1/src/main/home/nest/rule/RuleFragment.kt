package com.example.nm1.src.main.home.nest.rule

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.config.BaseFragment
import com.example.nm1.databinding.FragmentRuleBinding

class RuleFragment: BaseFragment<FragmentRuleBinding>(FragmentRuleBinding::bind, R.layout.fragment_rule){
    private var dataList = ArrayList<RuleData>()
    private lateinit var adapter: RuleRVAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.adapter = RuleRVAdapter()
        this.adapter.submitList(this.dataList)
        binding.ruleRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.ruleRecyclerview.adapter = this.adapter


        val data1 = RuleData("1", "12시 이후 귀가시 미리 이야기한다")
        val data2 = RuleData("2", "아침 당번은 돌아가면서 한다")
        val data3 = RuleData("3", "분리수거는 매주 일요일")

        dataList.add(data1)
        dataList.add(data2)
        dataList.add(data3)

        this.adapter.notifyDataSetChanged()

        binding.ruleAddBtn.setOnClickListener {
            binding.ruleAddInputLayout.visibility = View.VISIBLE
            val num = (dataList.size + 1).toString()
            binding.ruleAddInputNum.text = num
            binding.ruleAddInputContent.setOnKeyListener(object: View.OnKeyListener{
                override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                    if((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                        val str = binding.ruleAddInputContent.text.toString()
                        val data = RuleData(num, str)
                        dataList.add(data)
                        adapter.notifyDataSetChanged()
                        binding.ruleAddInputContent.clearFocus()
                        binding.ruleAddInputLayout.visibility = View.GONE
                        binding.ruleAddInputContent.text.clear()

                        return true
                    }
                    return false
                }

            })

        }
    }

    override fun onResume() {
        super.onResume()
        if(dataList.size == 0){
            binding.ruleEmptyLayout.visibility = View.VISIBLE
        }else{
            binding.ruleEmptyLayout.visibility = View.GONE
        }
    }

}