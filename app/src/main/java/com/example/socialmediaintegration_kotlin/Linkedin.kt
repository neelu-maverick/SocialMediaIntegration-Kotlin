package com.example.socialmediaintegration_kotlin

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.socialmediaintegration_kotlin.viewmodels.EmailViewModel
import com.example.socialmediaintegration_kotlin.viewmodels.LinkedinViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.io.OutputStreamWriter
import java.net.URL
import java.util.concurrent.TimeUnit
import javax.net.ssl.HttpsURLConnection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import org.json.JSONTokener

@AndroidEntryPoint
class Linkedin : AppCompatActivity() {

    lateinit var linkedinAuthURLFull: String
    lateinit var linkedIndialog: Dialog
    lateinit var linkedinCode: String
    private lateinit var linkedinViewModel: LinkedinViewModel
    private lateinit var emailViewModel: EmailViewModel


    var id = ""
    var firstName = ""
    var lastName = ""
    var email = ""
    var profilePicURL = ""
    var accessToken = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val state = "linkedin" + TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis())
        linkedinAuthURLFull =
            LinkedInConstants.AUTHURL + "?response_type=code&client_id=" + LinkedInConstants.CLIENT_ID + "&scope=" + LinkedInConstants.SCOPE + "&state=" + state + "&redirect_uri=" + LinkedInConstants.REDIRECT_URI
        setupLinkedinWebviewDialog(linkedinAuthURLFull)

    }


    // Show LinkedIn login page in a dialog
    @SuppressLint("SetJavaScriptEnabled")
    fun setupLinkedinWebviewDialog(url: String) {
        linkedIndialog = Dialog(this)
        val webView = WebView(this)
        webView.isVerticalScrollBarEnabled = false
        webView.isHorizontalScrollBarEnabled = false
        webView.webViewClient = LinkedInWebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)
        linkedIndialog.setContentView(webView)
        linkedIndialog.show()
    }

    // A client to know about WebView navigations
    // For API 21 and above
    @Suppress("OverridingDeprecatedMember")
    inner class LinkedInWebViewClient : WebViewClient() {

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        override fun shouldOverrideUrlLoading(
            view: WebView?,
            request: WebResourceRequest?
        ): Boolean {
            if (request?.url.toString().startsWith(LinkedInConstants.REDIRECT_URI)) {
                handleUrl(request?.url.toString())

                // Close the dialog after getting the authorization code
                if (request?.url.toString().contains("?code=")) {
                    linkedIndialog.dismiss()
                }
                return true
            }
            return false
        }

        // For API 19 and below
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            if (url.startsWith(LinkedInConstants.REDIRECT_URI)) {
                handleUrl(url)

                // Close the dialog after getting the authorization code
                if (url.contains("?code=")) {
                    linkedIndialog.dismiss()
                }
                return true
            }
            return false
        }

        // Check webview url for access token code or error
        private fun handleUrl(url: String) {
            val uri = Uri.parse(url)

            if (url.contains("code")) {
                linkedinCode = uri.getQueryParameter("code") ?: ""
                Log.e("TAG", "Linkedin code ->$linkedinCode")

                linkedInRequestForAccessToken()
            } else if (url.contains("error")) {
                val error = uri.getQueryParameter("error") ?: ""
                Log.e("Error: ", error)
            }
        }
    }


    fun linkedInRequestForAccessToken() {
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
            Log.e("TAG: ", "accessToken $accessToken")

            val expiresIn = jsonObject.getInt("expires_in") //When the access token expires
            Log.d("expires in: ", expiresIn.toString())


            withContext(Dispatchers.Main) {
                // Get user's id, first name, last name, profile pic url
                fetchlinkedInUserProfile(accessToken)
            }
        }


    }

    fun fetchlinkedInUserProfile(token: String) {

        Log.e("TAG", "token $token")

        linkedinViewModel = ViewModelProvider(this).get(LinkedinViewModel::class.java)
           linkedinViewModel.getprofileDetail(token)

        linkedinViewModel?.data?.observe(this) {
            firstName = it.firstName.localized.en_US
            profilePicURL = it.profilePicture.displayImageX.elements.get(2)
                .identifiers.get(0).identifier

            fetchLinkedInEmailAddress(token)
        }

    }

    fun fetchLinkedInEmailAddress(token:String) {


        emailViewModel = ViewModelProvider(this).get(EmailViewModel::class.java)
         emailViewModel.getEmail(token)
        emailViewModel?.data?.observe(this) {
            email= it.elements.get(0).handle.emailAddress
            openDetailsActivity()
        }



    }

    fun openDetailsActivity() {
        val myIntent = Intent(baseContext, ProfileActivity::class.java)
        myIntent.putExtra("linkedin_id", id)
        myIntent.putExtra("name", firstName)
        myIntent.putExtra("linkedin_last_name", lastName)
        myIntent.putExtra("email", email)
        myIntent.putExtra("imageurl", profilePicURL)
        startActivity(myIntent)
        finish()
    }

}
