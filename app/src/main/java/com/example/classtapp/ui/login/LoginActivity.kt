package com.example.classtapp.ui.login

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.View
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.openActivity
import com.crocodic.core.extension.textOf
import com.crocodic.core.extension.tos
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.data.constant.Const
import com.example.classtapp.databinding.ActivityLoginBinding
import com.example.classtapp.ui.home.HomeActivity
import com.example.classtapp.ui.register.RegisterActivity
import com.example.classtapp.ui.trypackage.TryActivity
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding, LoginViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_login)
        getFCMToken()

        //Set Phone & Password
        val getPhone = session.getString(Const.LOGIN.PHONE)
        val getPassword = session.getString(Const.LOGIN.PASSWORD)
        binding.phone = getPhone
        binding.password = getPassword

        observe()

    }

    private fun getFCMToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(ContentValues.TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }
            // Get new FCM registration token
            val token = task.result
            token!!.let { session.setValue(Const.DEVICETOKEN.FCMTOKEN, it) }
            // Log and toast
//            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(ContentValues.TAG, "Token: $token")
            tos("Token: $token")
        })
    }

    override fun onStart() {
        super.onStart()
        //Set Phone & Password
        val getPhone = session.getString(Const.LOGIN.PHONE)
        val getPassword = session.getString(Const.LOGIN.PASSWORD)
        binding.phone = getPhone
        binding.password = getPassword
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

        loadingDialog.show("Logging in...")
        val getDeviceToken = session.getString(Const.DEVICETOKEN.FCMTOKEN)
        Log.d("DeviceToken", "TheDVToken: $getDeviceToken")
        viewModel.loginClasstApp(binding.etLoginNoHp.textOf(), binding.etLoginPassword.textOf(), getDeviceToken)
    }



    fun onClickLoginActivity(view: View?) {
        when (view) {
            binding.tvDaftarAkun -> openActivity<RegisterActivity> {  }
            binding.tvLogin -> validateForm()
            binding.tvLoginTry -> openActivity<TryActivity> {  }

        }
    }

}