package com.example.nm1.src.main.home.nest.rule

import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.nm1.databinding.DialogRuleWarningBinding

class RuleWarningDialog(ruleWarningInterface: RuleWarningInterface): DialogFragment() {
    private var binding: DialogRuleWarningBinding? = null
    private var ruleWarningInterface = ruleWarningInterface

    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DialogRuleWarningBinding.inflate(inflater, container, false)
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

        binding!!.ruleDialogCancelBtn.setOnClickListener {
            ruleWarningInterface.warningCancelClicked()
            dismiss()
        }

        binding!!.ruleDialogConfirmBtn.setOnClickListener {
            ruleWarningInterface.warningContinueClicked()
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()

        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.7).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }
}