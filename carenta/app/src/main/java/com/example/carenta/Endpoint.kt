package com.example.carenta

import android.app.DownloadManager.Request
import retrofit2.Response
import retrofit2.http.*



interface Endpoint {

    @POST("login/")
    suspend fun login(@Body data: LoginCreds ):Response<user?>

    @POST("signup/")
    suspend fun signup(@Body data: user ):Response<user?>

    @GET("getcar")
    suspend fun getCar():Response<List<Car>>
}