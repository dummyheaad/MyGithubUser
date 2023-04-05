package com.example.mygithubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.database.FavoriteUser
import com.example.mygithubuser.databinding.ActivityDetailBinding
import com.example.mygithubuser.helper.ViewModelFactory
import com.example.mygithubuser.insert.FavoriteUserAddDeleteViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel> {
        ViewModelFactory.getInstance(application)
    }

    private lateinit var favoriteUserAddDeleteViewModel: FavoriteUserAddDeleteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        supportActionBar?.title = getString(R.string.title_detail)

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.dataUser.observe(this) {detailUser ->
            setDetailUserData(detailUser)
        }

        val username = intent.getStringExtra("username").toString()
        detailViewModel.displayDetailUser(username)

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter

        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        favoriteUserAddDeleteViewModel = obtainViewModel(this@DetailActivity)

        val favoriteUser = FavoriteUser()
        var fabState = BORDER_FAVORITE

        detailViewModel.dataUser.observe(this) {detailUser ->
            favoriteUser.username = detailUser.login
            favoriteUser.avatarUrl = detailUser.avatarUrl
        }

        favoriteUserAddDeleteViewModel.getFavoriteUserByUsername(username).observe(this) { result ->
            if (result != null) {
                activityDetailBinding.fabFav.setImageResource(R.drawable.ic_favorite)
                fabState = FULL_FAVORITE
            }
        }

        activityDetailBinding.fabFav.setOnClickListener { view ->
            if (view.id == R.id.fab_fav) {
                    if (fabState == BORDER_FAVORITE) {
                        activityDetailBinding.fabFav.setImageResource(R.drawable.ic_favorite)
                        favoriteUserAddDeleteViewModel.insert(favoriteUser)
                        showToast(getString(R.string.add_fav))
                        fabState = FULL_FAVORITE
                    }
                    else if (fabState == FULL_FAVORITE) {
                        activityDetailBinding.fabFav.setImageResource(R.drawable.ic_favorite_border)
                        favoriteUserAddDeleteViewModel.delete(favoriteUser)
                        showToast(getString(R.string.remove_fav))
                        fabState = BORDER_FAVORITE
                    }
                }
            }
        }

    private fun showLoading(isLoading: Boolean) {
        activityDetailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun setDetailUserData(detailUser: DetailUserResponse) {
        val avatar = detailUser.avatarUrl
        val username = detailUser.login
        val name = detailUser.name
        val followers = resources.getString(R.string.followers, detailUser.followers)
        val following = resources.getString(R.string.following, detailUser.following)
        Glide.with(this@DetailActivity)
            .load(avatar)
            .circleCrop()
            .into(activityDetailBinding.ivAvatar)

        activityDetailBinding.apply {
            tvUsername.text = username
            tvName.text = name
            tvFollowers.text = followers
            tvFollowing.text = following
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteUserAddDeleteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteUserAddDeleteViewModel::class.java]
    }

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )

        const val FULL_FAVORITE = "full_favorite"
        const val BORDER_FAVORITE = "border_favorite"
    }
}