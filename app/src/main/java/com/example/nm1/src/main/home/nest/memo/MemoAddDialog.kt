package com.example.nm1.src.main.home.nest.memo

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
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.DialogFragment
import com.example.nm1.R
import com.example.nm1.databinding.DialogMemoAddBinding
import kotlinx.android.synthetic.main.dialog_memo_add.view.*

class MemoAddDialog(memoCustomDialogInterface: MemoAddInterface): DialogFragment() {

    private var memoCustomDialogInterface: MemoAddInterface? = null
    private var binding: DialogMemoAddBinding? = null

    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    private var color: Int? = null


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



        binding!!.memoDialogConfirmBtn.setOnClickListener {
            val str = binding!!.memoDialogContentEt.text.toString()
            this.memoCustomDialogInterface!!.onConfirmBtnClicked(str, color)
            dismiss()
        }

        binding!!.memoDialogColorRg.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.memo_dialog_radio_btn1 -> {
                    color = R.color.colorYellow
                }
                R.id.memo_dialog_radio_btn2 -> {
                    color = R.color.colorPink
                }
                R.id.memo_dialog_radio_btn3 -> {
                    color = R.color.colorBlue
                }
                R.id.memo_dialog_radio_btn4 -> {
                    color = R.color.colorPurple
                }
                R.id.memo_dialog_radio_btn5 -> {
                    color = R.color.colorGreen
                }
                R.id.memo_dialog_radio_btn6 -> {
                    color = R.color.orange
                }
            }
        }


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
                }

            }
        )
    }

    override fun onResume() {
        super.onResume()


        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}