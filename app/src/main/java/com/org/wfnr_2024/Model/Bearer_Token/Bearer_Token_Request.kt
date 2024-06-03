package com.org.wfnr_2024.Model.Bearer_Token

data class Bearer_Token_Request(
    val grantType:String?=null,
    val clientId :String?=null,
    val clientSecret :String?=null
)
