package com.nestmate.nm1.src.main.home.nest.todo

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*
import kotlin.collections.ArrayList

@Parcelize
data class Todo(
    val title:String,
    val day_list:ArrayList<Boolean>?,
    val day:Date?,
    val time:Date
)  : Parcelable