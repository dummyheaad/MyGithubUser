package com.example.mygithubuser.insert

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.mygithubuser.database.FavoriteUser
import com.example.mygithubuser.repository.FavoriteUserRepository

class FavoriteUserAddDeleteViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser> = mFavoriteUserRepository.getFavoriteUserByUsername(username)

    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }

    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
}