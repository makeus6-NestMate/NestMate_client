package com.example.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class OneDayTodo(@SerializedName("todoId") val todoId: Int,
                      @SerializedName("todo") val todo: String,
                      @SerializedName("deadline") val deadline: String)