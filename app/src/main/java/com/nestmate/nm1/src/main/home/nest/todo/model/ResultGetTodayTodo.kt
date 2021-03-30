package com.nestmate.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class ResultGetTodayTodo(@SerializedName("todo") val todo:List<TodayTodo>)
