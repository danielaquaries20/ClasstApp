package com.example.classtapp.ui.login

import android.os.Bundle
import android.view.View
import com.crocodic.core.extension.openActivity
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

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.tvDaftarAkun -> openActivity<RegisterActivity> {  }
            binding.tvLogin -> openActivity<HomeActivity> {  }
            binding.tvLoginTry -> openActivity<TryActivity> {  }

        }

        super.onClick(v)
    }

}