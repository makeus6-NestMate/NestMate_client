package com.nestmate.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class TodayTodo(@SerializedName("todoId") val todoId: Int,
                     @SerializedName("todo") val todo: String,
                     @SerializedName("deadline") val deadline: String,
                     @SerializedName("profileImg") val profileImg: String,
                     @SerializedName("nickname") val nickname:String,
                     @SerializedName("isRepeat") val isRepeat:Char
)