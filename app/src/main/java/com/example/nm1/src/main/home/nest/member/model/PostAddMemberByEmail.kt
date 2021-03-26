package com.example.nm1.src.main.home.nest.member.model

import com.google.gson.annotations.SerializedName

data class PostAddMemberByEmail(
    @SerializedName("email") val email: String
)