package com.example.nm1.src.main.home.nest.todo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.setFragmentResult
import com.example.nm1.R
import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.nest.todo.model.*
import com.example.nm1.util.LoadingDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class TodoManagerBottomSheet: BottomSheetDialogFragment(), TodoView {
    private lateinit var mLoadingDialog: LoadingDialog
    private val roomId = ApplicationClass.sSharedPreferences.getInt("roomId", 0)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        return inflater.inflate(R.layout.todo_manager_bottomsheet_editordelete, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val edit = view?.findViewById<ConstraintLayout>(R.id.todo_manager_layout_edit)
        val delete = view?.findViewById<ConstraintLayout>(R.id.todo_manager_layout_delete)

        edit?.setOnClickListener{
            val todoEditDialog = TodoEditDialog()
            val bundle = Bundle()
            bundle.putInt("todoId", arguments?.getInt("todoId")!!)
            bundle.putString("onedayorrepeat", arguments?.getString("onedayorrepeat"))

//          수정삭제 bottomsheet
            todoEditDialog.arguments = bundle
            todoEditDialog.show(parentFragmentManager, todoEditDialog.tag)
        }

        delete?.setOnClickListener{
            showLoadingDialog(requireContext())
            TodoService(this).tryDeleteOneDayTodo(roomId, arguments?.getInt("todoId")!!)
        }
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
        if (!response.isSuccess){
            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
        }
        else {
            dismissLoadingDialog()
            Toast.makeText(requireContext(), "삭제가 완료되었습니다", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }

    override fun onDeleteOneDayTodoFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        //this.dismiss()
    }

    override fun onDeleteRepeatTodoSuccess(response: DeleteRepeatTodoResponse) {
        TODO("Not yet implemented")
    }

    override fun onDeleteRepeatTodoFailure(message: String) {
        TODO("Not yet implemented")
    }
}