package com.khios.apikotlin.APi

import com.khios.apikotlin.Model.UserRequest
import com.khios.apikotlin.Model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface UserApi {
    @POST("api/users")
    fun createUser(@Body req: UserRequest) : Call<UserResponse>
    abstract fun createUser(): Call<UserResponse>

    @PUT("api/users/{id}")
    fun updateUser(@Path ("id") id: Int , @Body req: UserRequest) : Call<UserResponse>

    @DELETE("api/users/{id}")
    fun deleteUser(@Path ("id") id: Int) : Call<Int>
}