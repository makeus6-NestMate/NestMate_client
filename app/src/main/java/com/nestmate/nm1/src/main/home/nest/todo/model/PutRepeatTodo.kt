package com.nestmate.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class PutRepeatTodo(
    @SerializedName("todoId") val todoId:Int,
    @SerializedName("todo") val todo: String,
    @SerializedName("time") val time: String,
    @SerializedName("days") val days: String
)