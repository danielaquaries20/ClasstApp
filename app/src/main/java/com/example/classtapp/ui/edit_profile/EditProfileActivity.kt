package com.example.classtapp.ui.edit_profile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityEditProfileBinding

class EditProfileActivity : BaseActivity<ActivityEditProfileBinding, EditProfileViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_edit_profile)
    }
}