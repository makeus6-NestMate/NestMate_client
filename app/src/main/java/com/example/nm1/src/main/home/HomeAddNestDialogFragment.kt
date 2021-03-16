package com.example.nm1.src.main.home

import android.content.Context
import android.graphics.Point
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.nm1.databinding.DialogNestAddBinding
import com.example.nm1.src.main.home.model.AddNestResponse
import com.example.nm1.src.main.home.model.PostAddNestRequest

class HomeAddNestDialogFragment : DialogFragment(), HomeFragmentView {
    var windowManager: WindowManager? = null
    var display: Display? = null
    var size: Point? = null
    private lateinit var binding: DialogNestAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //false로 설정해 주면 화면밖 혹은 뒤로가기 버튼시 다이얼로그라 dismiss 되지 않는다.
        isCancelable = false
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DialogNestAddBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

//        취소버튼
        binding.homeAddnestBtnCancel.setOnClickListener {
            dismiss()
        }

////       확인버튼
//        val addNestRequest = PostAddNestRequest()
    }

    override fun onResume() {
        super.onResume()

        var params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onAddNestSuccess(response: AddNestResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddNestFailure(message: String) {
        TODO("Not yet implemented")
    }
}