package com.example.classtapp.ui.detail_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.crocodic.core.extension.openActivity
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityDetailProfileBinding
import com.example.classtapp.ui.edit_profile.EditProfileActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailProfileActivity : BaseActivity<ActivityDetailProfileBinding, DetailProfileViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_detail_profile)
    }

    fun onClickDetailProfileActivity(v: View?) {
        when (v) {
            binding.ivProfileBack -> onBackPressed()
            binding.tvProfileEditProfile -> openActivity<EditProfileActivity> {  }
        }

        super.onClick(v)
    }

}