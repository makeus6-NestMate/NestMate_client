package com.example.nm1.src.main.home.nest.rule

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.config.BaseFragment
import com.example.nm1.config.BaseResponse
import com.example.nm1.databinding.FragmentRuleBinding
import com.example.nm1.src.main.home.nest.rule.model.GetRuleResponse
import com.example.nm1.src.main.home.nest.rule.model.PostRuleRequest
import com.example.nm1.src.main.home.nest.rule.model.PutRuleRequest
import kotlinx.android.synthetic.main.rule_rv_item.view.*

class RuleFragment: BaseFragment<FragmentRuleBinding>(FragmentRuleBinding::bind, R.layout.fragment_rule), RuleView, RuleDialogInterface, RuleWarningInterface{
    private var dataList = ArrayList<RuleData>()
    private lateinit var adapter: RuleRVAdapter
    private var roomId = ApplicationClass.sSharedPreferences.getInt("roomId", -1)
    private var editPosition = -1
    private var isContinue = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        this.adapter = RuleRVAdapter()
        this.adapter.submitList(this.dataList, requireContext())
        binding.ruleRecyclerview.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.ruleRecyclerview.adapter = this.adapter

        this.adapter.setOnClickListener(onClicked)

        RuleService(this).tryGetRule(ApplicationClass.sSharedPreferences.getInt("roomId", -1))

        binding.ruleTitle1.text = "'" + ApplicationClass.sSharedPreferences.getString("roomName", "둥지") + "'"

        binding.ruleAddBtn.setOnClickListener {
            isContinue = true

            if(dataList.size > 15){
                val dialog = RuleWarningDialog(this)
                dialog.show(childFragmentManager, "warning")
            }


            if(isContinue){
                binding.ruleAddInputLayout.visibility = View.VISIBLE
                binding.ruleAddInputLayout.requestFocus()
                val num = (dataList.size + 1).toString()
                binding.ruleAddInputNum.text = num
                binding.ruleAddInputContent.setOnKeyListener(object: View.OnKeyListener{
                    override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                        if((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                            val str = binding.ruleAddInputContent.text.toString()
                            val request = PostRuleRequest(str)
                            val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                            imm.hideSoftInputFromWindow(binding.ruleAddInputContent.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)

                            val temp = RuleData(num, str, 99)
                            dataList.add(temp)
                            adapter.notifyDataSetChanged()

                            binding.ruleAddInputContent.clearFocus()
                            binding.ruleAddInputLayout.visibility = View.GONE
                            binding.ruleAddInputContent.text.clear()

                            RuleService(this@RuleFragment).tryPostRule(roomId, request)

                            return true
                        }
                        return false
                    }

                })
            }
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

    override fun onPostRuleSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {

                RuleService(this).tryGetRule(roomId)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPostRuleFailure(message: String) {
        showCustomToast(message)
    }

    override fun onGetRuleSuccess(response: GetRuleResponse) {
        when(response.code){
            200 -> {
                this.dataList.clear()
                for(item in response.result.rule){
                    val data = RuleData((this.dataList.size+1).toString(), item.rule, item.ruleId)
                    this.dataList.add(data)
                }
                this.adapter.notifyDataSetChanged()

                if(this.dataList.size == 0){
                    binding.ruleEmptyLayout.visibility = View.VISIBLE
                }else{
                    binding.ruleEmptyLayout.visibility = View.GONE
                }
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onGetRuleFailure(message: String) {
        showCustomToast(message)
    }

    override fun onDeleteRuleSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                RuleService(this).tryGetRule(roomId)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onDeleteRuleFailure(message: String) {
        showCustomToast(message)
    }

    override fun onPutRuleSuccess(response: BaseResponse) {
        when(response.code){
            200 -> {
                val item = binding.ruleRecyclerview.findViewHolderForAdapterPosition(editPosition)
                item!!.itemView.rule_rv_item_content_et.visibility = View.GONE
                item!!.itemView.rule_rv_item_content.visibility = View.VISIBLE

                RuleService(this).tryGetRule(roomId)
            }
            else -> {
                showCustomToast(response.message.toString())
            }
        }
    }

    override fun onPutRuleFailure(message: String) {
        showCustomToast(message)
    }

    private val onClicked = object: RuleRVAdapter.OnItemClickListener{
        override fun onClicked(position: Int, ruleNum: String, ruleId: Int) {
            val dialog = RuleDialog(position, ruleId, this@RuleFragment)
            dialog.show(parentFragmentManager, "rule")
        }

        override fun onEdited(content: String, position: Int, ruleId: Int) {
            dataList[position] = RuleData((position + 1).toString(), content, ruleId)
            adapter.notifyItemChanged(position)
            val request = PutRuleRequest(content)
            RuleService(this@RuleFragment).tryPutRule(roomId, ruleId, request)
        }
    }

    override fun onEditClicked(position: Int, ruleId: Int) {
        editPosition = position
        adapter.editItem(position, ruleId)
        val item = binding.ruleRecyclerview.findViewHolderForAdapterPosition(position)
        item!!.itemView.rule_rv_item_content.visibility = View.GONE
        item!!.itemView.rule_rv_item_content_et.visibility = View.VISIBLE
        item!!.itemView.rule_rv_item_content_et.text = Editable.Factory.getInstance().newEditable(item!!.itemView.rule_rv_item_content.text.toString())
        item!!.itemView.rule_rv_item_content_et.requestFocus()
        item!!.itemView.rule_rv_item_content_et.background = null

    }

    override fun onDeleteClicked(position: Int, ruleId: Int) {
        this.dataList.removeAt(position)
        this.adapter.notifyItemRemoved(position)
        RuleService(this).tryDeleteRule(roomId, ruleId)
    }

    override fun warningCancelClicked() {
        isContinue = false
        binding.ruleAddInputContent.clearFocus()
        binding.ruleAddInputLayout.visibility = View.GONE
        binding.ruleAddInputContent.text.clear()

    }

    override fun warningContinueClicked() {
        isContinue = true
    }

}