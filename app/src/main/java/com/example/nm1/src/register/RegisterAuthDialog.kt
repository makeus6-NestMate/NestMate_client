package com.example.nm1.src.register

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.nm1.R
import com.example.nm1.databinding.DialogRegisterAuthBinding

class RegisterAuthDialog(message: String, isSuccess: Boolean): DialogFragment() {
    private var binding: DialogRegisterAuthBinding? = null
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    var message = message
    var isSuccess = isSuccess

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogRegisterAuthBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding!!.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        binding!!.registerDialogConfirmBtn.setOnClickListener {
            dismiss()
        }
        binding!!.registerDialogCancelBtn.setOnClickListener {
            dismiss()
        }

        if(isSuccess){
            binding!!.registerDialogImg.setImageResource(R.drawable.dialog_ok)
            binding!!.registerDialogResponseTv.text = message
        }else{
            binding!!.registerDialogImg.setImageResource(R.drawable.dialog_fail)
            binding!!.registerDialogResponseTv.text = message
        }

    }

    override fun onResume() {
        super.onResume()
        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.75).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams

    }
}