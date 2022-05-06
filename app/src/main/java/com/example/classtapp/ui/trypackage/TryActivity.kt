package com.example.classtapp.ui.trypackage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.SearchView
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityTryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TryActivity : BaseActivity<ActivityTryBinding, TryViewModel>(),
    SearchView.OnQueryTextListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_try)
    }

//    private fun setSearchView() {
//        val search = binding.layoutTry
//        val searchView = search as? SearchView
//        searchView?.isSubmitButtonEnabled = true
//        searchView?.setOnQueryTextListener(this)
//    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }

//    override fun onClick(v: View?) {
//        when(v) {
//            binding.ivTrySearch -> setSearchView()
//        }
//
//        super.onClick(v)
//    }
}