package com.example.mygithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithubuser.databinding.ActivityFavoriteUserBinding
import com.example.mygithubuser.helper.ViewModelFactory

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var activityFavoriteUserBinding: ActivityFavoriteUserBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFavoriteUserBinding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(activityFavoriteUserBinding.root)

        supportActionBar?.title = getString(R.string.title_favorite)

        val layoutManager = LinearLayoutManager(this)
        activityFavoriteUserBinding.rvUserFav.layoutManager = layoutManager

        val favoriteUserViewModel = obtainViewModel(this@FavoriteUserActivity)
        favoriteUserViewModel.getAllFavoriteUser().observe(this) { favoriteUserList ->
            if (favoriteUserList != null) {
                val items = arrayListOf<ItemsItem>()
                for (user in favoriteUserList) {
                    val item = ItemsItem(login = user.username, avatarUrl = user.avatarUrl.toString())
                    items.add(item)
                }
                activityFavoriteUserBinding.rvUserFav.adapter = UserAdapter(items)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserViewModel::class.java]
    }
}