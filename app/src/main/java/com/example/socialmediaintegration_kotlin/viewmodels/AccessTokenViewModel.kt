package com.example.socialmediaintegration_kotlin.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.socialmediaintegration_kotlin.LinkedInConstants
import java.io.OutputStreamWriter
import java.net.URL
import javax.inject.Inject
import javax.net.ssl.HttpsURLConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener

class AccessTokenViewModel @Inject constructor() {

    var token = ""


    fun getAccesstoken(linkedinCode:String){
        GlobalScope.launch(Dispatchers.Default) {
            val grantType = "authorization_code"
            val postParams =
                "grant_type=" + grantType + "&code=" + linkedinCode + "&redirect_uri=" + LinkedInConstants.REDIRECT_URI + "&client_id=" + LinkedInConstants.CLIENT_ID + "&client_secret=" + LinkedInConstants.CLIENT_SECRET
            val url = URL(LinkedInConstants.TOKENURL)
            val httpsURLConnection = withContext(Dispatchers.IO) { url.openConnection() as HttpsURLConnection }
            httpsURLConnection.requestMethod = "POST"
            httpsURLConnection.setRequestProperty(
                "Content-Type",
                "application/x-www-form-urlencoded"
            )
            httpsURLConnection.doInput = true
            httpsURLConnection.doOutput = true
            val outputStreamWriter = OutputStreamWriter(httpsURLConnection.outputStream)
            withContext(Dispatchers.IO) {
                outputStreamWriter.write(postParams)
                outputStreamWriter.flush()
            }
            val response = httpsURLConnection.inputStream.bufferedReader()
                .use { it.readText() }  // defaults to UTF-8
            val jsonObject = JSONTokener(response).nextValue() as JSONObject

            val accessToken = jsonObject.getString("access_token") //The access token
            Log.d("TAG: ", "accessToken $accessToken")

            val expiresIn = jsonObject.getInt("expires_in") //When the access token expires
            Log.d("expires in: ", expiresIn.toString())


            withContext(Dispatchers.Main) {
                // Get user's id, first name, last name, profile pic url
                generatedToken(accessToken)
            }


        }

    }

     fun generatedToken(tokens :String) {
        token = tokens
    }
}