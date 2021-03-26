package com.example.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class CockMember(@SerializedName("memberId") val memberId: Int,
                      @SerializedName("nickname") val nickname: String
)
{
    override fun toString(): String = nickname
}