package com.example.nm1.src.main.home.nest.memo

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.nm1.R
import com.example.nm1.databinding.MemoDeleteDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class MemoDeleteDialog(context: Context, memoDeleteInterface: MemoDeleteInterface): BottomSheetDialogFragment() {
    private lateinit var binding: MemoDeleteDialogBinding
    private var memoDeleteInterface: MemoDeleteInterface = memoDeleteInterface

    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = MemoDeleteDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        binding.memoDeleteBtn.setOnClickListener {
            this.memoDeleteInterface.onDeleteClicked(true)
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

//        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
//        val deviceWidth = size!!.x
//        params?.width = (deviceWidth*0.9).toInt()
//        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            (view?.parent as ViewGroup).background = ColorDrawable(Color.TRANSPARENT)
        }
        return dialog
    }

}