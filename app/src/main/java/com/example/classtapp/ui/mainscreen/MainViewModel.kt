package com.example.classtapp.ui.mainscreen

import androidx.lifecycle.viewModelScope
import com.example.classtapp.api.ApiService
import com.example.classtapp.base.activity.BaseViewModel
import com.example.classtapp.data.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : BaseViewModel(apiService) {

    fun checkLogin(result: (isLogin: Boolean) -> Unit) = viewModelScope.launch {
        result(userDao.isLogin())
    }


}