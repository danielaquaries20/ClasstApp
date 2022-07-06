package com.example.classtapp.ui.mainscreen

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.tos
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityMainBinding
import com.example.classtapp.ui.home.HomeActivity
import com.example.classtapp.ui.login.LoginActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_main)

//        val token = FirebaseMessaging.getInstance().token
//        Log.d("TokenFCM", "Token: $token")
//        tos("Token: $token")

    }

    fun onClickMainActivity(v: View?) {
        when (v) {
            binding.btnMasukApp -> {
                viewModel.checkLogin { isLogin ->
                    if (isLogin) {
                        openActivity<HomeActivity>()
                    } else {
                        openActivity<LoginActivity>()
                    }
                }
            }
        }
        super.onClick(v)
    }

}