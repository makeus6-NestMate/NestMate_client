package com.example.nm1.src.main.home.nest.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nm1.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MemPlusBottomSheet: BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.member_bottomsheetdialog_plus, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

//        activity -> fragment -> activity 순으로 데이터 전달

//        val loginchball = view?.findViewById<CheckBox>(R.id.login_chb_all)
//        val loginchbfirst = view?.findViewById<CheckBox>(R.id.login_chb_first)
//        val loginchbsecond = view?.findViewById<CheckBox>(R.id.login_chb_second)
//        val loginchbthird = view?.findViewById<CheckBox>(R.id.login_chb_third)
//        val loginchbfourth = view?.findViewById<CheckBox>(R.id.login_chb_fourth)
//        val loginchbfifth = view?.findViewById<CheckBox>(R.id.login_chb_fifth)
//        val loginchbsixth = view?.findViewById<CheckBox>(R.id.login_chb_sixth)
//
//        loginchball?.setOnClickListener{
//            if (!(loginchball.isChecked)){
//                loginchball.isChecked = true
//                loginchbfirst!!.isChecked = true
//                loginchbsecond!!.isChecked = true
//                loginchbthird!!.isChecked = true
//                loginchbfourth!!.isChecked = true
//                loginchbfifth!!.isChecked = true
//                loginchbsixth!!.isChecked = true
//            }
//            else{
//                loginchball.isChecked = false
//                loginchbfirst!!.isChecked = false
//                loginchbsecond!!.isChecked = false
//                loginchbthird!!.isChecked = false
//                loginchbfourth!!.isChecked = false
//                loginchbfifth!!.isChecked = false
//                loginchbsixth!!.isChecked = false
//            }
//        }
//
//        view?.findViewById<ConstraintLayout>(R.id.login_agree_layout_allagree)?.setOnClickListener {
////           체크 되어있지 않은 상태이면 -> 체크 상태로 바꿔야함
//            if (!(loginchball!!.isChecked)){
//                loginchball.isChecked = true
//                loginchbfirst!!.isChecked = true
//                loginchbsecond!!.isChecked = true
//                loginchbthird!!.isChecked = true
//                loginchbfourth!!.isChecked = true
//                loginchbfifth!!.isChecked = true
//                loginchbsixth!!.isChecked = true
//            }
//            else{
//                loginchball.isChecked = false
//                loginchbfirst!!.isChecked = false
//                loginchbsecond!!.isChecked = false
//                loginchbthird!!.isChecked = false
//                loginchbfourth!!.isChecked = false
//                loginchbfifth!!.isChecked = false
//                loginchbsixth!!.isChecked = false
//            }
//        }
//        view?.findViewById<Button>(R.id.login_info_btn_next3)?.setOnClickListener {
////           사용자 정보 넘기기 -> Intent
//
//            dismiss()
//
//            val intent = Intent(activity, LoginInsertCertiNumActivity::class.java)
//
//            intent.putExtra("phoneNumber", arguments?.getString("phoneNumber"))
//            intent.putExtra("name", arguments?.getString("name"))
//            intent.putExtra("birth", arguments?.getString("birth"))
//            intent.putExtra("gender", arguments?.getChar("gender"))
//            intent.putExtra("telocodeName", arguments?.getString("telocodeName"))
//            intent.putExtra("isAgreement", arguments?.getChar("isAgreement"))
//
//            startActivity(intent)
//            activity?.finish()
//        }
    }
}