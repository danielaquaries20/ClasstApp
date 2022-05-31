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
    val id: Int?,

    @Expose
    @SerializedName("name")
    val name: String?,

    @Expose
    @SerializedName("photo")
    val photo: String?,

    @Expose
    @SerializedName("school")
    val school: String?,

    @Expose
    @SerializedName("description")
    val description: String?,

    @Expose
    @SerializedName("likes")
    val likes: Int?,

    @Expose
    @SerializedName("like_by_you")
    val likeByYou: Boolean?,

    @Expose
    @SerializedName("total_like")
    val totalLike: Int?,

    @Expose
    @SerializedName("phone")
    val phone: String?

) : Parcelable