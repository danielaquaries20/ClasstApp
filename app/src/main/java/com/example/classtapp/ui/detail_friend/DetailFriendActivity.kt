package com.example.classtapp.ui.detail_friend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityDetailFriendBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFriendActivity : BaseActivity<ActivityDetailFriendBinding, DetailFriendViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_detail_friend)
    }
}