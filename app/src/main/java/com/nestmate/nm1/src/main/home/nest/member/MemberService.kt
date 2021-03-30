package com.nestmate.nm1.src.main.home.nest.member

import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.nestmate.nm1.src.main.home.nest.member.model.GetMemberResponse
import com.nestmate.nm1.src.main.home.nest.member.model.PostAddMemberByEmail
import com.nestmate.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MemberService(val view: MemberView) {
    fun tryAddMemberByEmail(roomId:Int, postAddMemberByEmail: PostAddMemberByEmail){
       val memberInterface = ApplicationClass.sRetrofit.create(MemberInterface::class.java)
        memberInterface.postAddMemberByEmail(roomId, postAddMemberByEmail).enqueue(object: Callback<ResponseAddMemberByEmail> {
           override fun onResponse(call: Call<ResponseAddMemberByEmail>, response: Response<ResponseAddMemberByEmail>) {
               view.onAddMemberByEmailSuccess(response.body() as ResponseAddMemberByEmail)
           }

           override fun onFailure(call: Call<ResponseAddMemberByEmail>, t: Throwable) {
               view.onAddMemberByEmailFailure(t.message ?: "통신 오류")
           }
       })
    }

    fun tryDeleteMeFromNest(roomId:Int){
        val memberInterface = ApplicationClass.sRetrofit.create(MemberInterface::class.java)
        memberInterface.deleteMeFromNest(roomId).enqueue(object: Callback<DeleteMeFromNestResponse> {
            override fun onResponse(call: Call<DeleteMeFromNestResponse>, response: Response<DeleteMeFromNestResponse>) {
                view.onDeleteMeFromNestSuccess(response.body() as DeleteMeFromNestResponse)
            }

            override fun onFailure(call: Call<DeleteMeFromNestResponse>, t: Throwable) {
                view.onDeleteMeFromNestFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetMember(roomId:Int){
        val memberInterface = ApplicationClass.sRetrofit.create(MemberInterface::class.java)
        memberInterface.getMember(roomId).enqueue(object: Callback<GetMemberResponse> {
            override fun onResponse(call: Call<GetMemberResponse>, response: Response<GetMemberResponse>) {
                view.onGetMemberSuccess(response.body() as GetMemberResponse)
            }

            override fun onFailure(call: Call<GetMemberResponse>, t: Throwable) {
                view.onGetMemberFailure(t.message ?: "통신 오류")
            }
        })
    }
}