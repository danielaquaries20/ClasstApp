package com.example.classtapp.ui.detail_profile

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.extension.toObject
import com.example.classtapp.api.ApiService
import com.example.classtapp.base.activity.BaseViewModel
import com.example.classtapp.data.user.User
import com.example.classtapp.data.user.UserDao
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson
) : BaseViewModel(apiService) {

    val user = userDao.getUser()

    //Real API
    fun getUser() =
        viewModelScope.launch {
            apiResponse.postValue(ApiResponse().responseLoading())
            val userId = userDao.getUserId()
            apiService.getUserById(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ApiObserver(true) {
                    override fun onSuccess(t: String) {
                        val responseJson = JSONObject(t)

                        val apiStatus = responseJson.getInt(ApiCode.STATUS)
                        val apiMessage = responseJson.getString(ApiCode.MESSAGE)
//                        val apiInfo = responseJson.getString("info")

                        android.util.Log.d("ApiResponse", "ResponseJson : $responseJson")
                        android.util.Log.d("ApiResponse", "ApiStatus : $apiStatus")
                        android.util.Log.d("ApiResponse", "ApiMessage : $apiMessage")

                        if (apiStatus == ApiCode.SUCCESS || apiStatus == 201) {
                            val getUser =
                                responseJson.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                            saveUser(getUser)
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


    fun logoutClasstApp() = viewModelScope.launch {
        apiResponse.postValue(ApiResponse().responseLoading())
        apiService.logoutClasstApp()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : ApiObserver(true) {
                override fun onSuccess(t: String) {
                    val responseJson = JSONObject(t)

                    val apiStatus = responseJson.getInt(ApiCode.STATUS)
                    val apiMessage = responseJson.getString(ApiCode.MESSAGE)

//                    Log.d("ApiResponse", "ResponseJson : $responseJson")
//                    Log.d("ApiResponse", "ApiStatus : $apiStatus")
//                    Log.d("ApiResponse", "ApiMessage : $apiMessage")

                    android.util.Log.d("ApiResponse", "ResponseJson : $responseJson")
                    android.util.Log.d("ApiResponse", "ApiStatus : $apiStatus")
                    android.util.Log.d("ApiResponse", "ApiMessage : $apiMessage")

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

    private fun saveUser(user: User) = viewModelScope.launch {
        userDao.insert(user.copy(idRoom = 1))
    }

}