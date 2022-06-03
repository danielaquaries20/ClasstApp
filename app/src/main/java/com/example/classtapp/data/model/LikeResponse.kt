package com.example.classtapp.data.model

import com.google.gson.annotations.SerializedName

class LikeResponse(

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("liked")
    val liked: Boolean? = null,

    @field:SerializedName("status")
    val status: Int? = null
)