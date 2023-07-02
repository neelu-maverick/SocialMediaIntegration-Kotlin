package com.example.socialmediaintegration_kotlin.api

import com.example.socialmediaintegration_kotlin.model.EmailResponseModel
import com.example.socialmediaintegration_kotlin.model.NewResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {


    @GET("me?projection=(id,firstName,lastName,profilePicture(displayImage~:playableStreams))")
    suspend fun getdata(
        @Query("oauth2_access_token") token :String
    ): Response<NewResponse>


//    @GET("emailAddress?q=members&projection=(elements*(handle~))&oauth2_access_token=AQWY3fjOphvH5kycSSYMuuoDOrWXy5ZjL7gqTM90a8aOKikhv6Zz3Vs4E1V5_sKwV5Cx4n79TLsmmLyjj_J6BYzyFhEKsspArk1erJIxC-I9jJHP_jDXfUHkctKDeqcLNbhsnNgNtHEijz_AoL0i8k_ywkxygSar8XroNOzXvEgo8-ZvDQBKLRwSR0_Ywgsm7P5bDLZacQq8V-SnX82og2GxvMT_ZxQ4BIQeB84-hkf1g7Kh3Dg9t1gjJPoQaNyoyILYZIgmTpOsy5bgQEeUvDV7rkctOC5GmtWvEi_Bc7qHeGeYTl8NuLGG6bsg8mvVI80QQqJ_AeY0gL2Ua9_KO0ZA1Xc5uA")
    @GET("emailAddress?q=members&projection=(elements*(handle~))")
    suspend fun getemail(
        @Query("oauth2_access_token") token :String
    ): Response<EmailResponseModel>
}