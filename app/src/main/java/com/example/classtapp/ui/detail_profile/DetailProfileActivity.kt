package com.example.classtapp.ui.detail_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityDetailProfileBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailProfileActivity : BaseActivity<ActivityDetailProfileBinding, DetailProfileViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_detail_profile)
    }
}