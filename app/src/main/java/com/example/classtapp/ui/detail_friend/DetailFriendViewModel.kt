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
    val likeData = MutableLiveData<LikeResponse>()

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
                        // TODO: 2/19/2022 Log untuk mengecek response likeByYou

//                        val user = responseJson.getJSONObject(ApiCode.DATA).toObject<User>(gson)
//                        saveUser(user)

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