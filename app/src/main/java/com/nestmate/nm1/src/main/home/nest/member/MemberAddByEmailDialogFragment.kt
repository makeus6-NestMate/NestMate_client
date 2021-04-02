package com.nestmate.nm1.src.main.home.nest.member

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.databinding.DialogMemberAddByemailBinding
import com.nestmate.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.nestmate.nm1.src.main.home.nest.member.model.GetMemberResponse
import com.nestmate.nm1.src.main.home.nest.member.model.PostAddMemberByEmail
import com.nestmate.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail
import com.nestmate.nm1.util.LoadingDialog
import com.nestmate.nm1.util.onMyTextChanged

class MemberAddByEmailDialogFragment : DialogFragment(), MemberView {
    private var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var color = 0
    private lateinit var mLoadingDialog: LoadingDialog
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    private lateinit var binding: DialogMemberAddByemailBinding

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
        binding = DialogMemberAddByemailBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

//        취소버튼
        binding.memberAddBtnCancel.setOnClickListener {
            dismiss()
        }

//       확인버튼
        binding.memberAddEdtEmail.onMyTextChanged {
            if(binding.memberAddEdtEmail.text.isNotEmpty()){
                binding.memberAddBtnConfirm.isEnabled = true //버튼 활성화
                binding.memberAddBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_orange_bg)
            }else{
                binding.memberAddBtnConfirm.isEnabled = false
                binding.memberAddBtnConfirm.setBackgroundResource(R.drawable.memo_dialog_btn_grey_bg)
            }
        }

        binding.memberAddBtnConfirm.setOnClickListener {
            val postAddMemberByEmail = PostAddMemberByEmail(binding.memberAddEdtEmail.text.toString().trim())
            showLoadingDialog(requireContext())
            MemberService(this).tryAddMemberByEmail(roomId, postAddMemberByEmail)
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

    override fun onAddMemberByEmailSuccess(response: ResponseAddMemberByEmail) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), "멤버 초대 완료", Toast.LENGTH_SHORT).show()
        val bundle = bundleOf("addmember_ok" to "ok")
        // 요청키로 수신측의 리스너에 값을 전달
        setFragmentResult("addmember", bundle)
        this.dismiss()
    }

    override fun onAddMemberByEmailFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onDeleteMeFromNestSuccess(response: DeleteMeFromNestResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteMeFromNestFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetMemberSuccess(response: GetMemberResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetMemberFailure(message: String) {
        TODO("Not yet implemented")
    }
}