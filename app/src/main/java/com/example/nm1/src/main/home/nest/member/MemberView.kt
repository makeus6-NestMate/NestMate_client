package com.example.nm1.src.main.home.nest.member

import com.example.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.example.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail

interface MemberView {
//   멤버 초대(이메일)
    fun onAddMemberByEmailSuccess(response: ResponseAddMemberByEmail)
    fun onAddMemberByEmailFailure(message: String)

//   둥지 나가기
    fun onDeleteMeFromNestSuccess(response: DeleteMeFromNestResponse)
    fun onDeleteMeFromNestFailure(message: String)
}