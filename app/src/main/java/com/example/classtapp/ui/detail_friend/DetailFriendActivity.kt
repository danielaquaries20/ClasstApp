package com.example.classtapp.ui.detail_friend

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        binding.data = intent.getParcelableExtra(Const.BUNDLE.FRIEND)

        observe()
    }

    private fun observe() {
        viewModel.apiResponse.observe(this) {
            when (it.status) {
                ApiStatus.LOADING -> {
//                    it.message?.let { msg -> loadingDialog.show(msg) }
                }
                ApiStatus.SUCCESS -> {
//                    loadingDialog.dismiss()
//                    finish()

//                    viewModel.likeData.observe(this){
//                        Log.d("checkResponse", "LikedResponse : $it")
//                        when (it.liked) {
//                            true -> {
//                                binding.ivLike.setImageResource(R.drawable.ic_baseline_favorite)
//                                tos("Liked")
//                                setResult(7)
//                            }
//                            false -> {
//                                binding.ivLike.setImageResource(R.drawable.ic_baseline_favorite_border)
//                                tos("Unliked")
//                                setResult(7)
//                            }
//                        }
//                    }
                }
                ApiStatus.WRONG, ApiStatus.ERROR -> {
//                    it.message?.let { msg -> loadingDialog.setResponse(msg) }
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
        }
        super.onClick(v)
    }

}