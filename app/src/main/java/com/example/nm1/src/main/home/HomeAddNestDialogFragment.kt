package com.example.nm1.src.main.home

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.example.nm1.R
import com.example.nm1.databinding.DialogNestAddBinding
import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.GetNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest
import com.example.nm1.util.LoadingDialog
import com.example.nm1.util.onMyTextChanged

class HomeAddNestDialogFragment : DialogFragment(), HomeFragmentView {
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var color = 0
    lateinit var mLoadingDialog: LoadingDialog

    private lateinit var binding: DialogNestAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNestAddBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

//       색 고르기
        binding.homeAddnestRadiogroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.home_addnest_radio_btn1 -> {
                    color = R.color.peach
                    Log.d("확인", color.toString())
                }
                R.id.home_addnest_radio_btn2 -> {
                    color = R.color.light_orange
                }
                R.id.home_addnest_radio_btn3 -> {
                    color = R.color.light_blue
                }
                R.id.home_addnest_radio_btn4 -> {
                    color = R.color.light_purple
                }
                R.id.home_addnest_radio_btn5 -> {
                    color = R.color.sky
                }
                R.id.home_addnest_radio_btn6 -> {
                    color = R.color.light_green
                }
            }
        }

//        취소버튼
        binding.homeAddnestBtnCancel.setOnClickListener {
            dismiss()
        }

//       확인버튼
        binding.homeAddnestEdtName.onMyTextChanged {
            if(binding.homeAddnestEdtName.text.isNotEmpty() && color!=0){
                binding.homeAddnestBtnConfirm.isEnabled = true //버튼 활성화
                binding.homeAddnestBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
            }else{
                binding.homeAddnestBtnConfirm.isEnabled = false
                binding.homeAddnestBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
            }
        }

        binding.homeAddnestBtnConfirm.setOnClickListener {
            showLoadingDialog(requireContext())
            val hexColor = "#"+Integer.toHexString(ContextCompat.getColor(requireContext(), color)).substring(2)
            val addNestRequest = PostAddNestRequest(hexColor, binding.homeAddnestEdtName.text.toString())
            HomeService(this).tryAddNest(addNestRequest)
        }
    }

    override fun onResume() {
        super.onResume()

        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x

        params?.width = (deviceWidth*0.75).toInt()

        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    override fun onAddNestSuccess(response: AddNestResponse) {
        dismissLoadingDialog()
        this.dismiss()
        val bundle = bundleOf("addnest_ok" to "ok")
        // 요청키로 수신측의 리스너에 값을 전달
        setFragmentResult("addnest", bundle)
    }

    override fun onAddNestFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetNestSuccess(response: GetNestResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetNestFailure(message: String) {
        TODO("Not yet implemented")
    }
}