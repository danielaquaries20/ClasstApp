package com.example.classtapp.ui.mainscreen

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import com.crocodic.core.extension.openActivity
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityMainBinding
import com.example.classtapp.ui.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_main)

//        //fullscreen
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN
//        )
//
//        //transparant status bar
//        window.setFlags(
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        )

//        Handler(mainLooper).postDelayed({
//            openActivity<LoginActivity>{}
//        }, 3000)

    }

    fun onClickMainActivity(v: View?) {
        when (v) {
            binding.btnMasukApp -> {
                binding.linearMainTwo.visibility = View.VISIBLE

                Handler(mainLooper).postDelayed({
                    binding.linearMainTwo.visibility = View.GONE
                    openActivity<LoginActivity> {}
                }, 1000)
            }

//            binding.btnMasukApp -> {
//                openActivity<LoginActivity> {}
//            }

        }

        super.onClick(v)
    }

}