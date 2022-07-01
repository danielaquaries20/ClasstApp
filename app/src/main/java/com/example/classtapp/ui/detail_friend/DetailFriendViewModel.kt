package com.example.classtapp.ui.detail_friend

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.crocodic.core.api.ApiCode
import com.crocodic.core.api.ApiObserver
import com.crocodic.core.api.ApiResponse
import com.crocodic.core.data.CoreSession
import com.crocodic.core.extension.toObject
import com.crocodic.core.helper.log.Log
import com.example.classtapp.api.ApiService
import com.example.classtapp.base.activity.BaseViewModel
import com.example.classtapp.data.model.LikeResponse
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
class DetailFriendViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao,
    private val gson: Gson,
    private val session: CoreSession
) : BaseViewModel(apiService) {

    val userAccount = userDao.getUser()
    val friendAccount = userDao.getFirend()
    val likeData = MutableLiveData<LikeResponse>()

    //Real API
    fun getFriend(id: Int?) =
        viewModelScope.launch {
            apiResponse.postValue(ApiResponse().responseLoading())
            apiService.getUserById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ApiObserver(true) {
                    override fun onSuccess(t: String) {
                        val responseJson = JSONObject(t)

                        val apiStatus = responseJson.getInt(ApiCode.STATUS)
                        val apiMessage = responseJson.getString(ApiCode.MESSAGE)
//                        val apiInfo = responseJson.getString("info")

                        if (apiStatus == ApiCode.SUCCESS || apiStatus == 201) {
                            val getFriend = responseJson.getJSONObject(ApiCode.DATA).toObject<User>(gson)
                            saveFriend(getFriend)
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

    fun likeFriend(id: Int?) =
        viewModelScope.launch {
            apiResponse.postValue(ApiResponse().responseLoading("Process..."))
            apiService.likeFriendClasstApp(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeWith(object : ApiObserver(true) {
                    override fun onSuccess(t: String) {
                        val responseJson = JSONObject(t)

                        val apiStatus = responseJson.getInt(ApiCode.STATUS)
                        val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                        if (apiStatus == 201||apiStatus == ApiCode.SUCCESS) {
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



    private fun saveFriend(user: User) = viewModelScope.launch {
        userDao.insert(user.copy(idRoom = 2))
    }


    //Dummy API
    fun like(id: Int?, id_i_like: Int?) = viewModelScope.launch {
        apiResponse.postValue(ApiResponse().responseLoading("like..."))
        apiService.like(id, id_i_like)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribeWith(object : ApiObserver(true) {
                override fun onSuccess(t: String) {
                    val responseJson = JSONObject(t)

                    val apiStatus = responseJson.getInt(ApiCode.STATUS)
                    val apiMessage = responseJson.getString(ApiCode.MESSAGE)

                    if (apiStatus == ApiCode.SUCCESS) {
                        val like = responseJson.toObject<LikeResponse>(gson)
                        likeData.postValue(like)
                        Log.d("CekLike", "$like")

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