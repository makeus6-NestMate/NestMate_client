package com.example.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class PutOneDayTodo(
    @SerializedName("todoId") val todoId: Int,
    @SerializedName("todo") val todo: String,
    @SerializedName("time") val time: String
)