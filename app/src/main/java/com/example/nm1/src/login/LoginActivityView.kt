package com.example.nm1.src.login

import com.example.nm1.src.login.model.LoginResponse

interface LoginActivityView {
    fun onPostLoginSuccess(response: LoginResponse)
    fun onPostLoginFailure(message: String)
}