package com.example.socialmediaintegration_kotlin.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaintegration_kotlin.model.NewResponse
import com.example.socialmediaintegration_kotlin.repository.Repo

class LinkedinViewModelFactory(private val repo: Repo):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(NewResponse::class.java)) {
            LinkedinViewModel(this.repo) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }


}