package com.example.classtapp.ui.detail_profile

import com.example.classtapp.api.ApiService
import com.example.classtapp.base.activity.BaseViewModel
import com.example.classtapp.data.user.UserDao
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class DetailProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val userDao: UserDao
) : BaseViewModel(apiService) {
}