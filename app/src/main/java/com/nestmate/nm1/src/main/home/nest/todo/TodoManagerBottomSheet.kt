package com.nestmate.nm1.src.main.home.nest.todo

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.nestmate.nm1.R
import com.nestmate.nm1.config.ApplicationClass
import com.nestmate.nm1.src.main.home.nest.todo.model.*
import com.nestmate.nm1.util.LoadingDialog

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
            bundle.putString("todotitle", arguments?.getString("todotitle"))

//          수정삭제 bottomsheet
            todoEditDialog.arguments = bundle
            todoEditDialog.show(parentFragmentManager, todoEditDialog.tag)
            this.dismiss()
        }

        delete?.setOnClickListener{
            showLoadingDialog(requireContext())
            if (arguments?.getString("onedayorrepeat")=="oneday") { //하루만에서 띄운경우
                TodoService(this).tryDeleteOneDayTodo(roomId, arguments?.getInt("todoId")!!)
            }
            else{ //반복에서 띄운 경우
                TodoService(this).tryDeleteRepeatTodo(roomId, arguments?.getInt("todoId")!!)
            }
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
        TODO("Not yet implemented")
    }

    override fun onPostCockFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun onGetCockMemberSuccess(response: GetCockMemberResponse) {
        TODO("Not yet implemented")
    }

    override fun onGetCockMemberFailure(message: String) {
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
        dismissLoadingDialog()
        if (!response.isSuccess){
            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
        }
        else {
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
        dismissLoadingDialog()
        if (!response.isSuccess){
            Toast.makeText(requireContext(), response.message, Toast.LENGTH_SHORT).show()
        }
        else {
            Toast.makeText(requireContext(), "삭제가 완료되었습니다", Toast.LENGTH_SHORT).show()
            this.dismiss()
        }
    }

    override fun onDeleteRepeatTodoFailure(message: String) {
        dismissLoadingDialog()
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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