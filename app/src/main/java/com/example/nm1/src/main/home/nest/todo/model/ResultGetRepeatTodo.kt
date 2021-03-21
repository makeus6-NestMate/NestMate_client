package com.example.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class ResultGetRepeatTodo(@SerializedName("todo") val todo:List<RepeatTodo>)
