package com.example.mygithubuser

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mygithubuser.databinding.ActivityDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

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

        val bundle = Bundle()
        bundle.putString("username", username)

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

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}