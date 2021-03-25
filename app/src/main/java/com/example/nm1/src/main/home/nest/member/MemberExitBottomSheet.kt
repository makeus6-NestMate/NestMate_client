package com.example.nm1.src.main.home.nest.member

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.MainActivity
import com.example.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.example.nm1.src.main.home.nest.member.model.GetMemberResponse
import com.example.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail
import com.example.nm1.src.main.home.nest.todo.model.*
import com.example.nm1.util.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MemberExitBottomSheet: BottomSheetDialogFragment(), MemberView{
    private lateinit var mLoadingDialog: LoadingDialog
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.member_bottomsheet_exit, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val exit = view?.findViewById<ConstraintLayout>(R.id.member_layout_exit)

        exit?.setOnClickListener{
            showLoadingDialog(requireContext())
            MemberService(this).tryDeleteMeFromNest(roomId)
        }

    }

    private fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    override fun onAddMemberByEmailSuccess(response: ResponseAddMemberByEmail) {
        TODO("Not yet implemented")
    }

    override fun onAddMemberByEmailFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteMeFromNestSuccess(response: DeleteMeFromNestResponse) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), ApplicationClass.sSharedPreferences.getString("roomName", null)+"에서 나갔습니다", Toast.LENGTH_SHORT).show()
        this.dismiss()
//        메인홈페이지로 이동
        val intent = Intent(
            requireContext(),
            MainActivity::class.java
        )
        intent.flags =
            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
    }

    override fun onDeleteMeFromNestFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetMemberSuccess(response: GetMemberResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetMemberFailure(message: String) {
        TODO("Not yet implemented")
    }
}