package com.example.nm1.src.main.home.nest.member.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class Member(@Nullable @SerializedName("profileImg") val profileImg: String?,
                  @SerializedName("nickname") val nickname: String)