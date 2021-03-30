package com.nestmate.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class PostAddOneDayTodo(
    @SerializedName("time") val time: String,
    @SerializedName("todo") val todo: String,
    @SerializedName("roomId") val roomId: Int
)