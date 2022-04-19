package com.example.classtapp.data.user

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(

    @PrimaryKey
    @Expose
    @SerializedName("id_room")
    val idRoom: Int,

    @Expose
    @SerializedName("id")
    val id: Int?

) : Parcelable