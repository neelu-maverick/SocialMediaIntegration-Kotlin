package com.example.socialmediaintegration_kotlin.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.socialmediaintegration_kotlin.model.EmailResponseModel
import com.example.socialmediaintegration_kotlin.model.NewResponse
import com.example.socialmediaintegration_kotlin.repository.EmailRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltViewModel
class EmailViewModel @Inject constructor(private val emailRepo: EmailRepo) : ViewModel() {

    fun getEmail(token :String){
        viewModelScope.launch(Dispatchers.IO) {

            emailRepo.getUserData(token)
        }
    }

    val data: LiveData<EmailResponseModel>
        get() = emailRepo.emaildata

}