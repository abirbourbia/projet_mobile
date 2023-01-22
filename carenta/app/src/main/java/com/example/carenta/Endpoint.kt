package com.example.carenta

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface Endpoint {

    @POST("login/")
    suspend fun login(@Body data: LoginCreds):Response<user?>

    //@POST("signup/")
    //suspend fun signup(@Body data: user ):Response<user?>

    @GET("getcar")
    suspend fun getCar():Response<List<Car>>

    @Multipart
    @POST("adduser")
    suspend fun addPDV(@Part image: MultipartBody.Part,
                       @Part user:MultipartBody.Part):Response<String>


}