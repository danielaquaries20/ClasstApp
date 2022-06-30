package com.example.classtapp.ui.edit_profile

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.example.classtapp.api.ApiService
import com.example.classtapp.base.activity.BaseViewModel
import com.example.classtapp.data.user.User
import com.example.classtapp.data.user.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import java.io.File
import javax.inject.Inject


@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson,
    private val session: CoreSession
) : BaseViewModel(apiService) {

    val user = userDao.getUser()

    fun updateUserProfileClasstApp(
        name: String?,
        photo: File,
        password: String?,
        kelas: String?,
        phone: String?,
        bio: String?
    ) =
        viewModelScope.launch {
            apiResponse.postValue(ApiResponse().responseLoading("Updating..."))
            val fileBody = photo.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("photo", photo.name, fileBody)
            apiService.updateUserProfileClasstApp(
                "put",
                name,
                filePart,
                password,
                kelas,
                phone,
                bio
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ApiObserver(true) {
                    override fun onSuccess(t: String) {
                        val responseJson = JSONObject(t)

                        val apiStatus = responseJson.getInt(ApiCode.STATUS)
                        val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                        if (apiStatus == ApiCode.SUCCESS) {
                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage))
                        } else {
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage))
                        }
                    }

                    override fun onError(e: Throwable) {
                        apiResponse.postValue(ApiResponse().responseError(e))
                    }
                })
        }

    fun updateUserProfileClasstAppWithoutImage(
        name: String?,
        password: String?,
        kelas: String?,
        phone: String?,
        bio: String?
    ) =
        viewModelScope.launch {
            apiResponse.postValue(ApiResponse().responseLoading("Updating..."))
            apiService.updateUserProfileClasstAppWithoutImage(
                "put",
                name,
                password,
                kelas,
                phone,
                bio
            )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ApiObserver(true) {
                    override fun onSuccess(t: String) {
                        val responseJson = JSONObject(t)

                        val apiStatus = responseJson.getInt(ApiCode.STATUS)
                        val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                        if (apiStatus == ApiCode.SUCCESS) {
                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage))
                        } else {
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage))
                        }
                    }

                    override fun onError(e: Throwable) {
                        apiResponse.postValue(ApiResponse().responseError(e))
                    }
                })
        }


    fun update(name: String?, school: String?, description: String?, photo: File) =
        viewModelScope.launch {
            apiResponse.postValue(ApiResponse().responseLoading("Updating..."))
            val id = userDao.getUserId()
            val fileBody = photo.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            val filePart = MultipartBody.Part.createFormData("photo", photo.name, fileBody)
            apiService.update(id, name, school, description, filePart)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ApiObserver(true) {
                    override fun onSuccess(t: String) {
                        val responseJson = JSONObject(t)

                        val apiStatus = responseJson.getInt(ApiCode.STATUS)
                        val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                        if (apiStatus == ApiCode.SUCCESS) {
                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage))
                        } else {
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage))
                        }
                    }

                    override fun onError(e: Throwable) {
                        apiResponse.postValue(ApiResponse().responseError(e))
                    }
                })
        }

    fun updateNoImage(name: String?, school: String?, description: String?) =
        viewModelScope.launch {
            apiResponse.postValue(ApiResponse().responseLoading("Updating..."))
            val id = userDao.getUserId()
            apiService.updateNoImage(id, name, school, description)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ApiObserver(true) {
                    override fun onSuccess(t: String) {
                        val responseJson = JSONObject(t)

                        val apiStatus = responseJson.getInt(ApiCode.STATUS)
                        val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                        if (apiStatus == ApiCode.SUCCESS) {
                            apiResponse.postValue(ApiResponse().responseSuccess(apiMessage))
                        } else {
                            apiResponse.postValue(ApiResponse().responseWrong(apiMessage))
                        }
                    }

                    override fun onError(e: Throwable) {
                        apiResponse.postValue(ApiResponse().responseError(e))
                    }
                })
        }

//    private fun saveUser(user: User) = viewModelScope.launch {
//        userDao.insert(user.copy(idRoom = 1))
//    }

}