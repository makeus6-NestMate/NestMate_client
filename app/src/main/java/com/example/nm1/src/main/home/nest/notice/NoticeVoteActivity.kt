package com.example.nm1.src.main.home.nest.notice

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nm1.R
import com.example.nm1.config.BaseActivity
import com.example.nm1.databinding.ActivityNoticeVoteBinding
import com.example.nm1.src.main.home.nest.notice.voteItemRV.VoteItemData
import com.example.nm1.src.main.home.nest.notice.voteItemRV.VoteItemRVAdapter
import com.example.nm1.src.main.home.nest.rule.RuleData
import kotlinx.android.synthetic.main.activity_notice_vote.*
import java.sql.Timestamp
import java.text.SimpleDateFormat

class NoticeVoteActivity : BaseActivity<ActivityNoticeVoteBinding>(ActivityNoticeVoteBinding::inflate) {
    private var isNotice = true
    private var voteItemList = ArrayList<VoteItemData>()
    private lateinit var adapter: VoteItemRVAdapter

    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.noticeVoteToolbar.toolbarTitle.text = "공지/투표"

        binding.noticeVoteNoticeBtn.setOnClickListener {
            isNotice = true
            binding.noticeContentLayout.visibility = View.VISIBLE
            binding.voteContentLayout.visibility = View.GONE
            binding.noticeVoteNoticeBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
            binding.noticeVoteNoticeBtn.setTextColor(Color.WHITE)
            binding.noticeVoteVoteBtn.setTextColor(R.color.colorTextHint)
            binding.noticeVoteVoteBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_white_bg)
        }

        binding.noticeVoteVoteBtn.setOnClickListener {
            isNotice = false
            binding.noticeContentLayout.visibility = View.GONE
            binding.voteContentLayout.visibility = View.VISIBLE
            binding.noticeVoteNoticeBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_white_bg)
            binding.noticeVoteNoticeBtn.setTextColor(R.color.colorTextHint)
            binding.noticeVoteVoteBtn.setTextColor(Color.WHITE)
            binding.noticeVoteVoteBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
        }

        binding.noticeVoteContentEt.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if(binding.noticeVoteContentEt.text.length > 0){
                    binding.noticeVoteConfirmBtn.setBackgroundResource(R.drawable.roundrec_design_active_bg)
                }else{
                    binding.noticeVoteConfirmBtn.setBackgroundResource(R.drawable.roundrec_design_inactive_bg)
                }

                var lines = binding!!.noticeVoteContentEt.lineCount
                if(lines > 5){
                    binding!!.noticeVoteContentEt.text.delete(binding!!.noticeVoteContentEt.selectionEnd -1,
                        binding!!.noticeVoteContentEt.selectionStart)
                    val imm = this@NoticeVoteActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding!!.noticeVoteContentEt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)

                }
            }

        })

        binding.noticeVoteConfirmBtn.setOnClickListener {
            val format = SimpleDateFormat("mm월 dd일 HH시 mm분")
            val timestamp = Timestamp(System.currentTimeMillis()).time
            val data = NoticeVoteData(isNotice, binding.noticeVoteContentEt.text.toString(), format.format(timestamp).toString(), null)
            val intent = Intent()
            intent.putExtra("data", data)
            setResult(RESULT_OK, intent)
            finish()
        }



        this.adapter = VoteItemRVAdapter()
        this.adapter.submitList(this.voteItemList)
        binding.voteAddRecyclerview.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.voteAddRecyclerview.adapter = this.adapter

        binding.voteItemAddBtn.setOnClickListener {
            binding.voteAddItemLayout.visibility = View.VISIBLE
            binding.voteAddItemNum.text = (voteItemList.size + 1).toString() + "."
        }

        binding.voteAddItemEt.setOnKeyListener(object: View.OnKeyListener{
            override fun onKey(v: View?, keyCode: Int, event: KeyEvent?): Boolean {
                if((event?.action == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)){

                    val num = (voteItemList.size + 1).toString()
                    val str = binding.voteAddItemEt.text.toString()
                    val data = VoteItemData(num, str)
                    voteItemList.add(data)
                    adapter.notifyDataSetChanged()
                    binding.voteAddItemEt.clearFocus()
                    binding.voteAddItemLayout.visibility = View.GONE
                    binding.voteAddItemEt.text.clear()

                    val imm = this@NoticeVoteActivity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(binding!!.voteAddItemEt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)

                    return true
                }
                return false
            }

        })
    }
}