package com.example.classtapp.ui.login

import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.example.classtapp.api.ApiService
import com.example.classtapp.base.activity.BaseViewModel
import com.example.classtapp.data.constant.Const
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
class LoginViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson,
    private val session: CoreSession
) : BaseViewModel(apiService) {

    //Real API
    fun loginClasstApp(phone: String?, password: String?) = viewModelScope.launch {
        apiResponse.postValue(ApiResponse().responseLoading("Logging in..."))
        apiService.loginClasstApp(phone, password, "device token success")
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : ApiObserver(true) {
                override fun onSuccess(t: String) {
                    val responseJson = JSONObject(t)

                    val apiStatus = responseJson.getInt(ApiCode.STATUS)
                    val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                    if (apiStatus == ApiCode.SUCCESS) {
                        val user = responseJson.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                        val token = responseJson.getString("token")
                        token.let { session.setValue(Const.BEARERTOKEN.TOKEN, it) }
                        phone?.let { session.setValue(Const.LOGIN.PHONE, it) }
                        password?.let { session.setValue(Const.LOGIN.PASSWORD, it) }
                        saveUser(user)
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


    //Dummy API
    fun login(phone: String?, password: String?) = viewModelScope.launch {
        apiResponse.postValue(ApiResponse().responseLoading("Logging in..."))
        apiService.login(phone, password)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : ApiObserver(true) {
                override fun onSuccess(t: String) {
                    val responseJson = JSONObject(t)

                    val apiStatus = responseJson.getInt(ApiCode.STATUS)
                    val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                    if (apiStatus == ApiCode.SUCCESS) {
                        val user = responseJson.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                        phone?.let { session.setValue(Const.LOGIN.PHONE, it) }
                        password?.let { session.setValue(Const.LOGIN.PASSWORD, it) }
                        saveUser(user)
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