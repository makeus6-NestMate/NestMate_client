package com.example.nm1.src.main.home.nest.member

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
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
    }

    override fun onDeleteMeFromNestFailure(message: String) {
        dismissLoadingDialog()
    }
}