package com.nestmate.nm1.src.main.home.nest.todo

import android.R
import android.content.Context
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.databinding.DialogTodoCockBinding
import com.nestmate.nm1.src.main.home.nest.todo.model.*
import com.nestmate.nm1.util.LoadingDialog

class TodoCockDialogFragment : DialogFragment(), TodoView {
    private var windowManager: WindowManager? = null
    private var display: Display? = null
    var size: Point? = null
    private lateinit var binding: DialogTodoCockBinding
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)
    var memberId:Int?=null
    private lateinit var mLoadingDialog: LoadingDialog

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
        binding = DialogTodoCockBinding.inflate(inflater, container, false)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        windowManager = activity?.getSystemService(Context.WINDOW_SERVICE) as? WindowManager
        display = windowManager!!.defaultDisplay
        size = Point()
        display!!.getSize(size)

        showLoadingDialog(requireContext())
        TodoService(this).tryGetCockMember(roomId)

        binding.todoBtnCockcancel.setOnClickListener {
            dismiss()
        }

        return binding.root

    }

    override fun onResume() {
        super.onResume()

        val params: ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size!!.x
        params?.width = (deviceWidth*0.75).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    private fun showLoadingDialog(context: Context) {
        mLoadingDialog = LoadingDialog(context)
        mLoadingDialog.show()
    }

    private fun dismissLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }

    override fun onAddOneDayTodoSuccess(response: AddOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onAddRepeatTodoSuccess(response: AddRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onAddRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetOneDayTodoSuccess(response: GetOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetRepeatTodoSuccess(response: GetRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetTodayTodoSuccess(response: GetTodayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetTodayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCompleteTodoSuccess(response: PostTodoCompleteResponse) {
        TODO("Not yet implemented")
    }

    override fun onPostCompleteTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPostCockSuccess(response: PostCockResponse) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), "콕 찌르기 완료", Toast.LENGTH_SHORT).show()
        this.dismiss()
    }

    override fun onPostCockFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onGetCockMemberSuccess(response: GetCockMemberResponse) {
        dismissLoadingDialog()
        val list = mutableListOf<CockMember>()
        val all_member = CockMember(0, "모두에게")
        list.add(0, all_member) //모두에게를 맨 위로
        for (i in response.result.member){
            list.add(i) //멤버 모두 추가
        }
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_dropdown_item, list)
        binding.todoCockSpinner.adapter = adapter

        binding.todoCockSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedMember = parent?.selectedItem
                if (selectedMember is CockMember) {
                    memberId = selectedMember.memberId
                }
            }
        }

        binding.todoBtnCockconfirm.setOnClickListener {
            showLoadingDialog(requireContext())
            val todoId = arguments?.getInt("todoId")
            //Log.d("hello", todoId.toString())
            if (todoId != null) {
                TodoService(this).tryPostCock(roomId, todoId, memberId!!)
            }
        }
    }

    override fun onGetCockMemberFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPutOneDayTodoSuccess(response: PutOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onPutRepeatTodoSuccess(response: PutRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onPutRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteOneDayTodoSuccess(response: DeleteOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteRepeatTodoSuccess(response: DeleteRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchOneDayTodoSuccess(response: GetSearchOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchRepeatTodoSuccess(response: GetSearchRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchTodoByDateSuccess(response: GetSearchTodoByDateResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetSearchTodoByDateFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllOneDayTodoSuccess(response: DeleteAllOneDayTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllOneDayTodoFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllRepeatTodoSuccess(response: DeleteAllRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteAllRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }
}