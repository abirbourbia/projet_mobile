package com.example.carenta

import retrofit2.HttpException
import java.io.File
import java.io.IOException
import java.util.Date

class user(
    var id:Int,
    var fullname :String?,
    var phonenumber:String?,
    var password: String,
    var birthdate:String?,
    var creditcard:String?,
    var expirationdate:String?,
)
    : java.io.Serializable

