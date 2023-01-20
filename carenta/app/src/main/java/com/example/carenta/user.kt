package com.example.carenta

import java.io.File
import java.util.Date

class user(
    var id:Int,
    var fullname :String?,
    var phonenumber:String?,
    var password: String,
    var birthdate:String?,
    var creditcard:String?,
    var expirationdate:String?,
    var drivinglicence: String
)
    : java.io.Serializable

