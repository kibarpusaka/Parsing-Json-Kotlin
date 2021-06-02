package com.khios.apikotlin

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.khios.apikotlin.APi.ComentApi
import com.khios.apikotlin.APi.UserApi
import com.khios.apikotlin.Model.Coment
import com.khios.apikotlin.Model.UserRequest
import com.khios.apikotlin.Model.UserResponse
import com.khios.apikotlin.Util.Retrof
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        inAction()
        getComentApi()
    }

    fun inAction(){
        btn_create.setOnClickListener {
            createUser()
        }

        btn_update.setOnClickListener {
            updateUser()
        }

        btn_delete.setOnClickListener {
            deleteUser()
        }
    }

    fun getComentApi(){
        val retrof =  Retrof().getRetroClientInstance("https://jsonplaceholder.typicode.com/").create(ComentApi::class.java)
        retrof.getComments().enqueue(object : Callback<List<Coment>>{
            override fun onResponse(call: Call<List<Coment>>, response: Response<List<Coment>>) {
                val coment = response.body()
                for (c in coment!!){
                    c.email?.let { Log.e("Hasil", it) }
                }
            }

            override fun onFailure(call: Call<List<Coment>>, t: Throwable) {
                Log.e("Failed", t.message.toString())
            }

        })
    }

    fun createUser(){
        val userReg = UserRequest()
        userReg.name = et_name.text.toString()
        userReg.job = et_job.text.toString()

        val retrof =  Retrof().getRetroClientInstance("https://reqres.in/").create(UserApi::class.java)
        retrof.createUser(userReg).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val user = response.body()
                tv_result_name.text = user!!.name.toString()
                tv_result_job.text = user!!.job.toString()
                tv_result_id.text = user!!.id.toString()
                tv_result_created.text = user!!.createdAt.toString()

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Failed", t.message.toString())
            }

        })
    }

    fun updateUser() {
        val userReg = UserRequest()
        userReg.name = et_name.text.toString()
        userReg.job = et_job.text.toString()

        val retrof =  Retrof().getRetroClientInstance("https://reqres.in/").create(UserApi::class.java)
        retrof.updateUser(et_id.text.toString().toInt(), userReg).enqueue(object : Callback<UserResponse>{
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                val user = response.body()
                tv_result_name.text = "name :" + user!!.name.toString()
                tv_result_job.text = "job :"+user!!.job.toString()
                tv_result_created.text = "update at :"+user!!.updatedAt .toString()

            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e("Failed", t.message.toString())
            }

        })
    }

    fun deleteUser() {
        val retrof =  Retrof().getRetroClientInstance("https://reqres.in/").create(UserApi::class.java)
        retrof.deleteUser(et_id.text.toString().toInt()).enqueue(object : Callback<Int>{
            override fun onResponse(call: Call<Int>, response: Response<Int>) {
                val res = response.code()
                Toast.makeText(applicationContext,"Data berhasil dihapus" + res, Toast.LENGTH_LONG).show()

            }

            override fun onFailure(call: Call<Int>, t: Throwable) {
                Log.e("Failed", t.message.toString())
            }

        })
    }
}