package com.example.socialmediaintegration_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaintegration_kotlin.model.NewResponse
import com.example.socialmediaintegration_kotlin.repository.Repo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LinkedinViewModel @Inject constructor(private val repo: Repo) : ViewModel() {

   fun getprofileDetail(token:String){
        viewModelScope.launch(Dispatchers.IO) {

            repo.getUserData(token)
        }
    }


    val data: LiveData<NewResponse>
        get() = repo.data

}