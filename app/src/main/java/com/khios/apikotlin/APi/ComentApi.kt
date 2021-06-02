package com.khios.apikotlin.APi

import com.khios.apikotlin.Model.Coment
import retrofit2.Call
import retrofit2.http.GET

interface ComentApi {
    @GET("comments")
    fun getComments() : Call<List<Coment>>
}