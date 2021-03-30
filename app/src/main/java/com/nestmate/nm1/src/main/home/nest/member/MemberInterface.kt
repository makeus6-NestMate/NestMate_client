package com.nestmate.nm1.src.main.home.nest.member

import com.nestmate.nm1.src.main.home.nest.member.model.DeleteMeFromNestResponse
import com.nestmate.nm1.src.main.home.nest.member.model.GetMemberResponse
import com.nestmate.nm1.src.main.home.nest.member.model.PostAddMemberByEmail
import com.nestmate.nm1.src.main.home.nest.member.model.ResponseAddMemberByEmail
import retrofit2.Call
import retrofit2.http.*

interface MemberInterface {
//    멤버 초대(이메일)
    @POST("/room/{roomId}/member")
    fun postAddMemberByEmail(@Path("roomId") roomId:Int, @Body params: PostAddMemberByEmail): Call<ResponseAddMemberByEmail>

//    둥지 나가기
    @DELETE("/room/{roomId}/member")
    fun deleteMeFromNest(@Path("roomId") roomId:Int): Call<DeleteMeFromNestResponse>

//   멤버 조회
    @GET("/room/{roomId}/member")
    fun getMember(@Path("roomId") roomId:Int): Call<GetMemberResponse>
}