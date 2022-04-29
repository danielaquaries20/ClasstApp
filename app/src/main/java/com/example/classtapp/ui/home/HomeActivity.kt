package com.example.classtapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityHomeBinding

class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_home)
    }
}