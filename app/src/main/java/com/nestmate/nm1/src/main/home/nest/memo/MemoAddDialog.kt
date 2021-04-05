package com.nestmate.nm1.src.main.home.nest.memo

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.DialogFragment
import com.nestmate.nm1.R
import com.nestmate.nm1.databinding.DialogMemoAddBinding
import kotlinx.android.synthetic.main.dialog_memo_add.view.*

class MemoAddDialog(isEdit: Boolean? = null, roomId: Int? = null, memoId: Int? = null, content: String? = null, color: String? = null, memoCustomDialogInterface: MemoAddInterface): DialogFragment() {

    private var memoCustomDialogInterface: MemoAddInterface? = null
    private var binding: DialogMemoAddBinding? = null

    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    private var isEdit = isEdit
    private var roomId = roomId
    private var memoId = memoId
    private var content = content

    private var color = color ?: "#ffd581"
    private var str: String? = null

//
//    companion object{
//        const val TAG = "MemoFragment"
//        fun newInstance(): MemoAddDialog{
//            return MemoAddDialog(memoCustomDialogInterface)
//        }
//    }

    init{
        this.memoCustomDialogInterface = memoCustomDialogInterface
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogMemoAddBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        if(isEdit != null && isEdit == true){
            binding!!.memoDialogContentEt.setText(content!!)
            str = content
            binding!!.memoDialogConfirmBtn.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
        }

        binding!!.memoDialogConfirmBtn.setOnClickListener {
            if(isEdit != null && isEdit == true){
                this.memoCustomDialogInterface!!.onConfirmBtnClicked(isEdit = true, memoId = memoId, message = str!!, color = color)
            }else{
                this.memoCustomDialogInterface!!.onConfirmBtnClicked(isEdit = false, memoId = -1, message = str!!, color = color)
            }
            dismiss()
        }

        binding!!.memoDialogCancelBtn.setOnClickListener {
            dismiss()
        }

        binding!!.memoDialogColorRg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.memo_dialog_radio_btn1 -> {
                    color = "#ffd581"
                }
                R.id.memo_dialog_radio_btn2 -> {
                    color = "#ffd5e5"
                }
                R.id.memo_dialog_radio_btn3 -> {
                    color = "#79e6ef"
                }
                R.id.memo_dialog_radio_btn4 -> {
                    color = "#c0adff"
                }
                R.id.memo_dialog_radio_btn5 -> {
                    color = "#4debbb"
                }
                R.id.memo_dialog_radio_btn6 -> {
                    color = "#ff9e81"
                }
            }
        }
        dialog?.setCancelable(false)

        binding!!.memoDialogContentEt.addTextChangedListener(
            object: TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                }

                override fun afterTextChanged(s: Editable?) {
                    var lines = binding!!.memoDialogContentEt.lineCount
                    if(lines > 5){
                        binding!!.memoDialogContentEt.text.delete(binding!!.memoDialogContentEt.selectionEnd -1,
                            binding!!.memoDialogContentEt.selectionStart)
                        val imm = activity?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                        imm.hideSoftInputFromWindow(binding!!.memoDialogContentEt.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS)

                    }

                    if(binding!!.memoDialogContentEt.text.isNotEmpty()){
                        binding!!.memoDialogConfirmBtn.isEnabled = true
                        binding!!.memoDialogConfirmBtn.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
                    }else{
                        binding!!.memoDialogConfirmBtn.isEnabled = false
                        binding!!.memoDialogConfirmBtn.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
                    }

                    str = binding!!.memoDialogContentEt.text.toString()
                }

            }
        )
    }

    override fun onResume() {
        super.onResume()


        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.7).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}