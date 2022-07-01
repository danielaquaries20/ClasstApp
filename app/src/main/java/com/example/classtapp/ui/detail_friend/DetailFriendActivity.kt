package com.example.classtapp.ui.detail_friend

import android.os.Bundle
import android.view.View
import com.crocodic.core.api.ApiStatus
import com.crocodic.core.extension.tos
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.data.constant.Const
import com.example.classtapp.databinding.ActivityDetailFriendBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFriendActivity : BaseActivity<ActivityDetailFriendBinding, DetailFriendViewModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_detail_friend)

        binding.friend = intent.getParcelableExtra(Const.BUNDLE.FRIEND)

        getFriend()

        viewModel.friendAccount.observe(this) { friend ->
            binding.data = friend
        }

        observe()
    }

    private fun getFriend() {
        val idFriend = binding.friend?.id
        viewModel.getFriend(idFriend)
    }

    private fun likeFriend() {
        val idFriend = binding.friend?.id
        viewModel.likeFriend(idFriend)
    }



    private fun observe() {
        viewModel.apiResponse.observe(this) {
            when (it.status) {
                ApiStatus.LOADING -> {
//                    tos("Loading...")
                }
                ApiStatus.SUCCESS -> {
//                    tos("Success")
                }
                ApiStatus.WRONG, ApiStatus.ERROR -> {
//                    tos("API Error")
                }
                else -> {
                    tos("Error")
                }
            }
        }
    }

    fun onClickDetailFriendActivity(v: View?) {
        when (v) {
            binding.ivDetailFriendBack -> onBackPressed()
            binding.ivDetailFriendLike -> {
                likeFriend()
                tos("Unliked", true)
                binding.ivDetailFriendLike.visibility = View.GONE
                binding.ivDetailFriendUnlike.visibility = View.VISIBLE
            }
            binding.ivDetailFriendUnlike -> {
                likeFriend()
                tos("Liked", true)
                binding.ivDetailFriendUnlike.visibility = View.GONE
                binding.ivDetailFriendLike.visibility = View.VISIBLE
            }
        }
        super.onClick(v)
    }
}