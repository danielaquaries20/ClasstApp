package com.example.classtapp.ui.detail_profile

import android.os.Bundle
import android.view.View
import com.crocodic.core.extension.createIntent
import com.crocodic.core.extension.openActivity
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.data.constant.Const
import com.example.classtapp.data.user.User
import com.example.classtapp.databinding.ActivityDetailProfileBinding
import com.example.classtapp.ui.edit_profile.EditProfileActivity
import com.example.classtapp.ui.mainscreen.MainActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailProfileActivity : BaseActivity<ActivityDetailProfileBinding, DetailProfileViewModel>() {

    private var self = ArrayList<User?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_detail_profile)

        viewModel.user.observe(this) { user ->
            binding.user = user
        }


    }

    fun onClickDetailProfileActivity(v: View?) {
        when (v) {
            binding.ivProfileBack -> onBackPressed()

//            binding.tvProfileEditProfile -> openActivity<EditProfileActivity> {
//                putExtra(Const.BUNDLE.SELF, binding.user)
//            }

            binding.tvProfileEditProfile -> activityLauncher.launch(createIntent<EditProfileActivity> {
                putExtra(Const.BUNDLE.SELF, binding.user)
            }) {
                if (it.resultCode == 7)
                    viewModel.user.observe(this) { user ->
                        binding.user = user
                    }
            }


            binding.ivProfileLogout -> {
                authLogoutSuccess()
                openActivity<MainActivity>()
                finishAffinity()
            }
        }

        super.onClick(v)
    }

}