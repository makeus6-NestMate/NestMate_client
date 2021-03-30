package com.nestmate.nm1.src.main.home.model

import androidx.annotation.Nullable
import com.google.gson.annotations.SerializedName

data class NestMember(@Nullable @SerializedName("profileImg") val profileImg: String,
                      @SerializedName("nickname") val nickname: String)