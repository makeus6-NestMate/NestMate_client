package com.example.nm1.src.main.home.nest.todo.model

import com.example.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetSearchTodoByDateResponse(@SerializedName("result") val result: ResultSearchTodoByDate): BaseResponse()
