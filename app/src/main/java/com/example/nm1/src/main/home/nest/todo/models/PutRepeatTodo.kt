package com.example.nm1.src.main.home.nest.todo.models

import com.google.gson.annotations.SerializedName

data class PutRepeatTodo(
    @SerializedName("time") val color: String,
    @SerializedName("days") val days: String,
    @SerializedName("todo") val todo: String,
    @SerializedName("roomId") val roomId: Int
)