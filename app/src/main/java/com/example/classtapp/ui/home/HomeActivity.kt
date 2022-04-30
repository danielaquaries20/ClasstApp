package com.example.classtapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.crocodic.core.extension.openActivity
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityHomeBinding
import com.example.classtapp.ui.detail_profile.DetailProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_home)
    }

    override fun onClick(v: View?) {

        when (v) {
            binding.ivHomePhotoProfile -> openActivity<DetailProfileActivity> {  }
        }

        super.onClick(v)
    }

}