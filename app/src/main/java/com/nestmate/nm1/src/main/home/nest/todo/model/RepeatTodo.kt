package com.nestmate.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class RepeatTodo(@SerializedName("todoId") val todoId: Int,
                      @SerializedName("todo") val todo: String,
                      @SerializedName("deadline") val deadline: String,
                      @SerializedName("day") val day: String,
                      @SerializedName("isOwner") val isOwner:Char)