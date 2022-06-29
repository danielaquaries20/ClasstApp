package com.example.classtapp.api

import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.*

interface ApiService {

    //Real API
    @FormUrlEncoded
    @POST("api/kelasku/login")
    fun loginClasstApp(
        @Field("phone") phone: String?,
        @Field("password") password: String?,
        @Field("device_token") deviceToken: String?
    ): Single<String>

    @FormUrlEncoded
    @POST("api/kelasku/register")
    fun registerClasstApp(
        @Field("name") name: String?,
        @Field("phone") phone: String?,
        @Field("password") password: String?,
        @Field("class") kelas: String?,
        @Field("bio") bio: String?
    ): Single<String>

    @GET("api/kelasku")
    fun getListUser(): Single<String>


    //Dummy API
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("phone") phone: String?,
        @Field("password") password: String?
    ): Single<String>

    @FormUrlEncoded
    @POST("register")
    fun register(
        @Field("name") name: String?,
        @Field("phone") phone: String?,
        @Field("password") password: String?
    ): Single<String>

    @FormUrlEncoded
    @POST("get-list-friends")
    fun friendList(
        @Field("users_id") id_user: Int?
    ): Single<String>

    @Multipart
    @POST("update-profile")
    fun update(
        @Query("id") id: Int?,
        @Query("name") name: String?,
        @Query("school") school: String?,
        @Query("description") description: String?,
        @Part photo: MultipartBody.Part?
    ): Single<String>

    @FormUrlEncoded
    @POST("update-profile")
    fun updateNoImage(
        @Field("id") id: Int?,
        @Field("name") name: String?,
        @Field("school") school: String?,
        @Field("description") description: String?
    ): Single<String>

    @FormUrlEncoded
    @POST("like")
    fun like(
        @Field("users_id") users_id: Int?,
        @Field("user_id_i_like") user_id_i_like: Int?
    ): Single<String>


}