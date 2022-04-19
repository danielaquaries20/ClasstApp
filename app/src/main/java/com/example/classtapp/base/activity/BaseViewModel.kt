package com.example.classtapp.base.activity

import androidx.lifecycle.viewModelScope
import com.crocodic.core.base.viewmodel.CoreViewModel
import com.example.classtapp.api.ApiService
import kotlinx.coroutines.launch

open class BaseViewModel(private val apiService: ApiService) : CoreViewModel() {
    override fun apiLogout() = viewModelScope.launch { }
    override fun apiRenewToken() = viewModelScope.launch { }
}