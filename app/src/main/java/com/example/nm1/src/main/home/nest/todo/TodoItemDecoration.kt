package com.example.nm1.src.main.home.nest.todo

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class TodoItemDecoration : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = 15
        outRect.bottom = 15
    }
}