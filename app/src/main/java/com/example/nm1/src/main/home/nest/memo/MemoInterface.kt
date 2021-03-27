package com.example.nm1.src.main.home.nest.memo

import com.example.nm1.config.BaseResponse
import com.example.nm1.src.main.home.nest.memo.model.GetMemoResponse
import com.example.nm1.src.main.home.nest.memo.model.PatchMemoRequest
import com.example.nm1.src.main.home.nest.memo.model.PostMemoRequest
import com.example.nm1.src.main.home.nest.memo.model.PutMemoRequest
import retrofit2.Call
import retrofit2.http.*

interface MemoInterface {

    @POST("/room/{roomId}/memo")
    fun postMemo(@Path("roomId") roomId: Int, @Body params: PostMemoRequest): Call<BaseResponse>

    @GET("room/{roomId}/memo")
    fun getMemo(@Path("roomId") roomId: Int): Call<GetMemoResponse>

    @DELETE("/room/{roomId}/memo/{memoId}")
    fun deleteMemo(@Path("roomId") roomId: Int, @Path("memoId") memoId: Int): Call<BaseResponse>

    @PATCH("/room/{roomId}/memo/{memoId}")
    fun patchMEmo(@Path("roomId") roomId: Int, @Path("memoId") memoId: Int, @Body params: PatchMemoRequest): Call<BaseResponse>

    @PUT("/room/{roomId}/memo/{memoId}")
    fun putMemo(@Path("roomId") roomId: Int, @Path("memoId") memoId: Int, @Body params: PutMemoRequest): Call<BaseResponse>
}