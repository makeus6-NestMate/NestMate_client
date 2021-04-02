package com.nestmate.nm1.src.main.home.nest.todo

interface TodoManagerInterface {
    fun onDeleteClicked(flag: Boolean, roomId: Int, todoId: Int, isRepeat:Char)
    fun onEditClicked(flag: Boolean, roomId: Int, todoId: Int, todo: String, time:String?, days:String?)
}