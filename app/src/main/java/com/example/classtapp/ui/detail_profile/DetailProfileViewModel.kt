package com.example.classtapp.ui.detail_profile

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.extension.toList
import com.crocodic.core.helper.log.Log
import com.example.classtapp.api.ApiService
import com.example.classtapp.base.activity.BaseViewModel
import com.example.classtapp.data.user.User
import com.example.classtapp.data.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.json.JSONObject
import javax.inject.Inject


@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : BaseViewModel(apiService) {

    val user = userDao.getUser()

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


}