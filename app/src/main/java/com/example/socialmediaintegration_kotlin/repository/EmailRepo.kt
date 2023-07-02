package com.example.socialmediaintegration_kotlin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.socialmediaintegration_kotlin.api.ApiInterface
import com.example.socialmediaintegration_kotlin.model.EmailResponseModel
import com.example.socialmediaintegration_kotlin.viewmodels.AccessTokenViewModel
import javax.inject.Inject

class EmailRepo @Inject constructor(private val apiInterface: ApiInterface ) {

    private val userEmailData = MutableLiveData<EmailResponseModel>()

    val emaildata: LiveData<EmailResponseModel>
        get() = userEmailData


    suspend fun getUserData(token:String) {


        val result = apiInterface.getemail(token)

        Log.e("TAG", "response is ${result.body()}")

        if (result.body() != null) {

            userEmailData.postValue(result.body())
        }
    }
}