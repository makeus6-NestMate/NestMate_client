package com.nestmate.nm1.src.main.home.nest.todo.model

import com.nestmate.nm1.config.BaseResponse
import com.google.gson.annotations.SerializedName

data class GetRepeatTodoResponse(@SerializedName("result") val result: ResultGetRepeatTodo): BaseResponse()
