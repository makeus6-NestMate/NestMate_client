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

    fun tryGetOneDayTodo(roomId:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getOneDayTodo(roomId).enqueue(object: Callback<GetOneDayTodoResponse> {
            override fun onResponse(call: Call<GetOneDayTodoResponse>, response: Response<GetOneDayTodoResponse>) {
                view.onGetOneDayTodoSuccess(response.body() as GetOneDayTodoResponse)
            }

            override fun onFailure(call: Call<GetOneDayTodoResponse>, t: Throwable) {
                view.onGetOneDayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun tryGetRepeatTodo(roomId:Int){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getRepeatTodo(roomId).enqueue(object: Callback<GetRepeatTodoResponse> {
            override fun onResponse(call: Call<GetRepeatTodoResponse>, response: Response<GetRepeatTodoResponse>) {
                view.onGetRepeatTodoSuccess(response.body() as GetRepeatTodoResponse)
            }

            override fun onFailure(call: Call<GetRepeatTodoResponse>, t: Throwable) {
                view.onGetRepeatTodoFailure(t.message ?: "통신 오류")
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

    fun trySearchGetOneDayTodo(roomId:Int, keyword:String){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getSearchOneDayTodo(roomId, keyword).enqueue(object: Callback<GetSearchOneDayTodoResponse> {
            override fun onResponse(call: Call<GetSearchOneDayTodoResponse>, response: Response<GetSearchOneDayTodoResponse>) {
                view.onGetSearchOneDayTodoSuccess(response.body() as GetSearchOneDayTodoResponse)
            }

            override fun onFailure(call: Call<GetSearchOneDayTodoResponse>, t: Throwable) {
                view.onGetSearchOneDayTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun trySearchGetRepeatTodo(roomId:Int, keyword:String){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getSearchRepeatTodo(roomId, keyword).enqueue(object: Callback<GetSearchRepeatTodoResponse> {
            override fun onResponse(call: Call<GetSearchRepeatTodoResponse>, response: Response<GetSearchRepeatTodoResponse>) {
                view.onGetSearchRepeatTodoSuccess(response.body() as GetSearchRepeatTodoResponse)
            }

            override fun onFailure(call: Call<GetSearchRepeatTodoResponse>, t: Throwable) {
                view.onGetSearchRepeatTodoFailure(t.message ?: "통신 오류")
            }
        })
    }

    fun trySearchTodoByDate(roomId:Int, date:String){
        val todoInterface = ApplicationClass.sRetrofit.create(TodoInterface::class.java)
        todoInterface.getSearchTodoByDate(roomId, date).enqueue(object: Callback<GetSearchTodoByDateResponse> {
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