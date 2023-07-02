package com.example.socialmediaintegration_kotlin.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.socialmediaintegration_kotlin.api.ApiInterface
import com.example.socialmediaintegration_kotlin.model.NewResponse
import com.google.gson.Gson
import javax.inject.Inject


class Repo @Inject constructor(private val apiInterface: ApiInterface) {

    private val userData = MutableLiveData<NewResponse>()

    val data: LiveData<NewResponse>
        get() = userData
    suspend fun getUserData(token:String){
        val result = apiInterface.getdata(token)

        Log.e("TAG","response is ${result.body()?.firstName}")

        if (result.body()!=null){

            userData.postValue(result.body())
        }
    }


//    fun getAccesstoken() {
//
//
//        GlobalScope.launch(Dispatchers.Default) {
//            val grantType = "authorization_code"
//            val postParams =
//                "grant_type=" + grantType + "&code=" + linkedinCode + "&redirect_uri=" + LinkedInConstants.REDIRECT_URI + "&client_id=" + LinkedInConstants.CLIENT_ID + "&client_secret=" + LinkedInConstants.CLIENT_SECRET
//            val url = URL(LinkedInConstants.TOKENURL)
//            val httpsURLConnection = withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
//            httpsURLConnection.requestMethod = "POST"
//            httpsURLConnection.setRequestProperty(
//                "Content-Type",
//                "application/x-www-form-urlencoded"
//            )
//            httpsURLConnection.doInput = true
//            httpsURLConnection.doOutput = true
//            val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
//            withContext(Dispatchers.IO) {
//                outputStreamWriter.write(postParams)
//                outputStreamWriter.flush()
//            }
//            val response = httpsURLConnection.inputStream.bufferedReader()
//                .use { it.readText() }  // defaults to UTF-8
//            val jsonObject = JSONTokener(response).nextValue() as JSONObject
//
//            val accessToken = jsonObject.getString("access_token") //The access token
//            Log.d("TAG: ", "accessToken $accessToken")
//
//
//            val expiresIn = jsonObject.getInt("expires_in") //When the access token expires
//            Log.d("expires in: ", expiresIn.toString())
//
//            withContext(Dispatchers.Main) {
//                // Get user's id, first name, last name, profile pic url
//
//            }
//
//
//        }
//        return getAccesstoken()
//    }
//
//    suspend fun getUserData() {
//        /*   val result = apiInterface.getdata()
//           if (result.body()!=null)
//           {
//               userData.postValue(result.body())
//           }*/
//
//
//        val tokenURLFull =
//            "https://api.linkedin.com/v2/me?projection=(id,firstName,lastName,profilePicture(displayImage~:playableStreams))&oauth2_access_token="
//        val url = URL(tokenURLFull)
//        val httpsURLConnection = withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
//        httpsURLConnection.requestMethod = "GET"
//        httpsURLConnection.doInput = true
//        httpsURLConnection.doOutput = false
//        val response = httpsURLConnection.inputStream.bufferedReader()
//            .use { it.readText() }  // defaults to UTF-8
//        val type = object : TypeToken<NewResponse>() {
//        }.type
//        val gson = Gson()
//        val json = gson.toJson(response)
//
//        val linkedInProfileModel = Gson().fromJson<NewResponse>(response.toString(), type)
//        /* val linkedInProfileModel =
//             Json.nonstrict.parse(LinkedInProfileModel.serializer(), response)*/
//
//
//        withContext(Dispatchers.Main) {
////            Log.d("LinkedIn Access Token: ", token)
//
//            // LinkedIn Id
//            val linkedinId = linkedInProfileModel.id
//            Log.d("LinkedIn Id: ", linkedinId)
////            id = linkedinId
//
//            // LinkedIn First Name
//            val linkedinFirstName = linkedInProfileModel.firstName.localized.en_US
//            Log.d("LinkedIn First Name: ", linkedinFirstName)
////            firstName = linkedinFirstName
//
//            // LinkedIn Last Name
//            val linkedinLastName = linkedInProfileModel.lastName.localized.en_US
//            Log.d("LinkedIn Last Name: ", linkedinLastName)
////            lastName = linkedinLastName
//
//            // LinkedIn Profile Picture URL
//            /*
//                 Change row of the 'elements' array to get diffrent size of the profile pic
//                 elements[0] = 100x100
//                 elements[1] = 200x200
//                 elements[2] = 400x400
//                 elements[3] = 800x800
//            */
//
//            val linkedinProfilePic =
//                linkedInProfileModel.profilePicture.displayImageX.elements.get(2)
//                    .identifiers.get(0).identifier
//            Log.d("LinkedIn Profile URL: ", linkedinProfilePic)
////            profilePicURL = linkedinProfilePic
//        }
//        userData.postValue(linkedInProfileModel)
//    }

}

