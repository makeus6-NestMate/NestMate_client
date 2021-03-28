package com.example.nm1.src.main.home.nest.todo

import com.example.nm1.config.ApplicationClass
import com.example.nm1.src.main.home.nest.todo.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TodoService(val view: TodoView) {
    fun tryAddOneDayTodo(roomId:Int, postAddOneDayTodo: PostAddOneDayTodo){
       val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.postAddOneDayTodo(roomId, postAddOneDayTodo).enqueue(object: Callback<AddOneDayTodoResponse> {
           override fun onResponse(call: Call<AddOneDayTodoResponse>, response: Response<AddOneDayTodoResponse>) {
               view.onAddOneDayTodoSuccess(response.body() as AddOneDayTodoResponse)
           }

           override fun onFailure(call: Call<AddOneDayTodoResponse>, t: Throwable) {
               view.onAddOneDayTodoFailure(t.message ?: "통신 오류")
           }
       })
    }

    fun tryAddRepeatTodo(roomId:Int, postAddRepeatTodo: PostAddRepeatTodo){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.postAddRepeatTodo(roomId, postAddRepeatTodo).enqueue(object: Callback<AddRepeatTodoResponse> {
            override fun onResponse(call: Call<AddRepeatTodoResponse>, response: Response<AddRepeatTodoResponse>) {
                view.onAddRepeatTodoSuccess(response.body() as AddRepeatTodoResponse)
            }

            override fun onFailure(call: Call<AddRepeatTodoResponse>, t: Throwable) {
                view.onAddRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetOneDayTodo(roomId:Int, page:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getOneDayTodo(roomId, page).enqueue(object: Callback<GetOneDayTodoResponse> {
            override fun onResponse(call: Call<GetOneDayTodoResponse>, response: Response<GetOneDayTodoResponse>) {
                view.onGetOneDayTodoSuccess(response.body() as GetOneDayTodoResponse)
            }

            override fun onFailure(call: Call<GetOneDayTodoResponse>, t: Throwable) {
                view.onGetOneDayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetRepeatTodo(roomId:Int, page:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getRepeatTodo(roomId, page).enqueue(object: Callback<GetRepeatTodoResponse> {
            override fun onResponse(call: Call<GetRepeatTodoResponse>, response: Response<GetRepeatTodoResponse>) {
                view.onGetRepeatTodoSuccess(response.body() as GetRepeatTodoResponse)
            }

            override fun onFailure(call: Call<GetRepeatTodoResponse>, t: Throwable) {
                view.onGetRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetTodayTodo(roomId:Int, page:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getTodayTodo(roomId, page).enqueue(object: Callback<GetTodayTodoResponse> {
            override fun onResponse(call: Call<GetTodayTodoResponse>, response: Response<GetTodayTodoResponse>) {
                view.onGetTodayTodoSuccess(response.body() as GetTodayTodoResponse)
            }

            override fun onFailure(call: Call<GetTodayTodoResponse>, t: Throwable) {
                view.onGetTodayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostCompleteTodo(roomId:Int, todoId:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.postTodoComplete(roomId, todoId).enqueue(object: Callback<PostTodoCompleteResponse> {
            override fun onResponse(call: Call<PostTodoCompleteResponse>, response: Response<PostTodoCompleteResponse>) {
                view.onPostCompleteTodoSuccess(response.body() as PostTodoCompleteResponse)
            }

            override fun onFailure(call: Call<PostTodoCompleteResponse>, t: Throwable) {
                view.onPostCompleteTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPostCock(roomId:Int, todoId:Int, memberId:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.postCock(roomId, todoId, memberId).enqueue(object: Callback<PostCockResponse> {
            override fun onResponse(call: Call<PostCockResponse>, response: Response<PostCockResponse>) {
                view.onPostCockSuccess(response.body() as PostCockResponse)
            }

            override fun onFailure(call: Call<PostCockResponse>, t: Throwable) {
                view.onPostCockFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetCockMember(roomId:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getCockMember(roomId).enqueue(object: Callback<GetCockMemberResponse> {
            override fun onResponse(call: Call<GetCockMemberResponse>, response: Response<GetCockMemberResponse>) {
                view.onGetCockMemberSuccess(response.body() as GetCockMemberResponse)
            }

            override fun onFailure(call: Call<GetCockMemberResponse>, t: Throwable) {
                view.onGetCockMemberFailure(t.message ?: "통신 오류")
            }
        })
    }


    fun tryPutOneDayTodo(roomId:Int, putOneDayTodo: PutOneDayTodo){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.putOneDayTodo(roomId, putOneDayTodo).enqueue(object: Callback<PutOneDayTodoResponse> {
            override fun onResponse(call: Call<PutOneDayTodoResponse>, response: Response<PutOneDayTodoResponse>) {
                view.onPutOneDayTodoSuccess(response.body() as PutOneDayTodoResponse)
            }

            override fun onFailure(call: Call<PutOneDayTodoResponse>, t: Throwable) {
                view.onPutOneDayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryPutRepeatTodo(roomId:Int, putRepeatTodo: PutRepeatTodo){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.putRepeatTodo(roomId, putRepeatTodo).enqueue(object: Callback<PutRepeatTodoResponse> {
            override fun onResponse(call: Call<PutRepeatTodoResponse>, response: Response<PutRepeatTodoResponse>) {
                view.onPutRepeatTodoSuccess(response.body() as PutRepeatTodoResponse)
            }

            override fun onFailure(call: Call<PutRepeatTodoResponse>, t: Throwable) {
                view.onPutRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteOneDayTodo(roomId:Int, todoId: Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.deleteOneDayTodo(roomId, todoId).enqueue(object: Callback<DeleteOneDayTodoResponse> {
            override fun onResponse(call: Call<DeleteOneDayTodoResponse>, response: Response<DeleteOneDayTodoResponse>) {
                view.onDeleteOneDayTodoSuccess(response.body() as DeleteOneDayTodoResponse)
            }

            override fun onFailure(call: Call<DeleteOneDayTodoResponse>, t: Throwable) {
                view.onDeleteOneDayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteRepeatTodo(roomId:Int, todoId: Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.deleteRepeatTodo(roomId, todoId).enqueue(object: Callback<DeleteRepeatTodoResponse> {
            override fun onResponse(call: Call<DeleteRepeatTodoResponse>, response: Response<DeleteRepeatTodoResponse>) {
                view.onDeleteRepeatTodoSuccess(response.body() as DeleteRepeatTodoResponse)
            }

            override fun onFailure(call: Call<DeleteRepeatTodoResponse>, t: Throwable) {
                view.onDeleteRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun trySearchGetOneDayTodo(roomId:Int, keyword:String, page:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getSearchOneDayTodo(roomId, keyword, page).enqueue(object: Callback<GetSearchOneDayTodoResponse> {
            override fun onResponse(call: Call<GetSearchOneDayTodoResponse>, response: Response<GetSearchOneDayTodoResponse>) {
                view.onGetSearchOneDayTodoSuccess(response.body() as GetSearchOneDayTodoResponse)
            }

            override fun onFailure(call: Call<GetSearchOneDayTodoResponse>, t: Throwable) {
                view.onGetSearchOneDayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun trySearchGetRepeatTodo(roomId:Int, keyword:String, page:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getSearchRepeatTodo(roomId, keyword, page).enqueue(object: Callback<GetSearchRepeatTodoResponse> {
            override fun onResponse(call: Call<GetSearchRepeatTodoResponse>, response: Response<GetSearchRepeatTodoResponse>) {
                view.onGetSearchRepeatTodoSuccess(response.body() as GetSearchRepeatTodoResponse)
            }

            override fun onFailure(call: Call<GetSearchRepeatTodoResponse>, t: Throwable) {
                view.onGetSearchRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun trySearchTodoByDate(roomId:Int, date:String, page:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getSearchTodoByDate(roomId, date, page).enqueue(object: Callback<GetSearchTodoByDateResponse> {
            override fun onResponse(call: Call<GetSearchTodoByDateResponse>, response: Response<GetSearchTodoByDateResponse>) {
                view.onGetSearchTodoByDateSuccess(response.body() as GetSearchTodoByDateResponse)
            }

            override fun onFailure(call: Call<GetSearchTodoByDateResponse>, t: Throwable) {
                view.onGetSearchTodoByDateFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteAllOneDayTodo(roomId:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.deleteAllOneDayTodo(roomId).enqueue(object: Callback<DeleteAllOneDayTodoResponse> {
            override fun onResponse(call: Call<DeleteAllOneDayTodoResponse>, response: Response<DeleteAllOneDayTodoResponse>) {
                view.onDeleteAllOneDayTodoSuccess(response.body() as DeleteAllOneDayTodoResponse)
            }

            override fun onFailure(call: Call<DeleteAllOneDayTodoResponse>, t: Throwable) {
                view.onDeleteAllOneDayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryDeleteAllRepeatTodo(roomId:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.deleteAllRepeatTodo(roomId).enqueue(object: Callback<DeleteAllRepeatTodoResponse> {
            override fun onResponse(call: Call<DeleteAllRepeatTodoResponse>, response: Response<DeleteAllRepeatTodoResponse>) {
                view.onDeleteAllRepeatTodoSuccess(response.body() as DeleteAllRepeatTodoResponse)
            }

            override fun onFailure(call: Call<DeleteAllRepeatTodoResponse>, t: Throwable) {
                view.onDeleteAllRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }
}