package com.example.classtapp.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import com.crocodic.core.extension.openActivity
import com.example.classtapp.R
import com.example.classtapp.base.activity.BaseActivity
import com.example.classtapp.databinding.ActivityHomeBinding
import com.example.classtapp.ui.detail_profile.DetailProfileActivity
import com.example.classtapp.ui.trypackage.TryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : BaseActivity<ActivityHomeBinding, HomeViewModel>(),
    SearchView.OnQueryTextListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setLayoutRes(R.layout.activity_home)

        setSupportActionBar(binding.toolbarHomeSearch)

    }

    override fun onCreateOptionsMenu(setMenu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, setMenu)

        val search = setMenu?.findItem(R.id.iconMenuSearch)
        val searchView = search?.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)

        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        TODO("Not yet implemented")
    }


    override fun onClick(v: View?) {

        when (v) {
            binding.ivHomePhotoProfile -> openActivity<DetailProfileActivity> { }
        }

        super.onClick(v)
    }


}