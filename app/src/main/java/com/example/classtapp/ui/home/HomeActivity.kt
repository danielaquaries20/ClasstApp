package com.example.classtapp.ui.home

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
import com.crocodic.core.base.adapter.CoreListAdapter
import com.crocodic.core.extension.createIntent
import com.crocodic.core.extension.openActivity
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.data.constant.Const
import com.example.classtapp.data.user.User
import com.example.classtapp.databinding.ActivityHomeBinding
import com.example.classtapp.databinding.ItemFriendBinding
import com.example.classtapp.ui.detail_friend.DetailFriendActivity
import com.example.classtapp.ui.detail_profile.DetailProfileActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(),
    SearchView.OnQueryTextListener {

    private var friend = ArrayList<User?>()
    private var friendAll = ArrayList<User?>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_home)

        viewModel.user.observe(this) { user ->
            binding.user = user
        }

        observe()
        initView()

        binding.svHomeSearch.setOnQueryTextListener(this)
        getData()
    }

    private fun getData() {
//        binding.isLoading = true
        viewModel.listFriend()
    }

    private fun initView() {
        binding.rvHomeListFriend.adapter =
            CoreListAdapter<ItemFriendBinding, User>(R.layout.item_friend).initItem(friend) { position, data ->
                activityLauncher.launch(createIntent<DetailFriendActivity> {
                    putExtra(Const.BUNDLE.FRIEND, data)
                }) {
                    if (it.resultCode == 7)
                        getData()
                }
//                openActivity<DetailFriendActivity> {
//                    putExtra(Const.BUNDLE.FRIEND, data)
//                }
            }

        binding.srHomeRefreshData.setOnRefreshListener {
            getData()
            Handler(mainLooper).postDelayed({
                binding.srHomeRefreshData.isRefreshing = false
            }, 1000)

        }


    }


    private fun observe() {
        binding.isLoading = true
        viewModel.friends.observe(this) {
            val sortName = it.sortedBy { sort ->
                sort.idRoom != 1
            }
            val filter = it.filter { it.idRoom != 1 }

            friend.clear()
            friendAll.clear()
            binding.rvHomeListFriend.adapter?.notifyDataSetChanged()

            friend.addAll(filter)
            friendAll.addAll(filter)
            Log.d("CekFriendAll", "DataFriendAll : $friendAll")
            binding.rvHomeListFriend.adapter?.notifyItemInserted(0)
            binding.isLoading = false
        }
    }


//    override fun onCreateOptionsMenu(setMenu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.menu_home, setMenu)
//
//        val search = setMenu?.findItem(R.id.iconMenuSearch)
//        val searchView = search?.actionView as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//
//        return true
//    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        Log.d("Keyword", "$newText")

        if (newText?.isNotEmpty() == true) {
            val filter = friendAll.filter { it?.name?.contains("$newText", true) == true }
            Log.d("CekFilter", "Keyword : $filter")
            friend.clear()
            binding.rvHomeListFriend.adapter?.notifyDataSetChanged()
            friend.addAll(filter)
            binding.rvHomeListFriend.adapter?.notifyItemInserted(0)

        } else if (newText?.isEmpty() == true) {
            friend.clear()
            binding.rvHomeListFriend.adapter?.notifyDataSetChanged()
            friend.addAll(friendAll)
            binding.rvHomeListFriend.adapter?.notifyItemInserted(0)
        }
        return false


    }


    fun onClickHomeActivity(v: View?) {

        when (v) {
            binding.ivHomePhotoProfile -> openActivity<DetailProfileActivity> { }

            binding.ivHomeSearch -> {
                binding.ivHomeSearch.visibility = View.GONE
                binding.layoutHomeSearch.visibility = View.VISIBLE
                binding.ivHomeSearch2.visibility = View.VISIBLE
            }

            binding.ivHomeCloseSearch -> {
                binding.ivHomeSearch2.visibility = View.GONE
                binding.layoutHomeSearch.visibility = View.GONE
                binding.ivHomeSearch.visibility = View.VISIBLE
            }

            binding.ivHomeSearch2 -> {
                binding.ivHomeSearch2.visibility = View.GONE
                binding.layoutHomeSearch.visibility = View.GONE
                binding.ivHomeSearch.visibility = View.VISIBLE
            }

        }

        super.onClick(v)
    }

}