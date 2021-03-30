package com.nestmate.nm1.src.main.home.nest.todo.model

import com.google.gson.annotations.SerializedName

data class ResultSearchGetOneDayTodo(@SerializedName("todo") val todo:List<OneDayTodo>)
