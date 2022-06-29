package com.example.classtapp.ui.login

import android.os.Bundle
import android.view.View
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityLoginBinding
import com.example.classtapp.ui.home.HomeActivity
import com.example.classtapp.ui.register.RegisterActivity
import com.example.classtapp.ui.trypackage.TryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_login)

        observe()

    }

    private fun observe() {
        viewModel.apiResponse.observe(this) {
            when (it.status) {
                ApiStatus.LOADING -> {
                    it.message?.let { msg -> loadingDialog.show(msg) }
                }
                ApiStatus.SUCCESS -> {
                    loadingDialog.dismiss()
                    openActivity<HomeActivity>()
                    finish()
                }
                ApiStatus.WRONG, ApiStatus.ERROR -> {
                    it.message?.let { msg -> loadingDialog.setResponse(msg) }
                }
            }
        }
    }

    private fun validateForm() {
        if (binding.etLoginNoHp.text?.isEmpty() == true) {
            binding.etLoginNoHp.error = "Form must filled"
        } else if (binding.etLoginPassword.text?.isEmpty() == true) {
            binding.etLoginPassword.error = "Form must filled"
        }

//        if (listOf(binding.etPhone, binding.etPassword).isEmptyRequired()) {
//            return
//        }

        loadingDialog.show("Logging in...")
        viewModel.loginClasstApp(binding.etLoginNoHp.textOf(), binding.etLoginPassword.textOf())
    }



    fun onClickLoginActivity(view: View?) {
        when (view) {
            binding.tvDaftarAkun -> openActivity<RegisterActivity> {  }
            binding.tvLogin -> validateForm()
            binding.tvLoginTry -> openActivity<TryActivity> {  }

        }
    }

}