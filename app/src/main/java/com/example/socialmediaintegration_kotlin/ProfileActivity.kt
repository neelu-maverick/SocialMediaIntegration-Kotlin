package com.example.socialmediaintegration_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.socialmediaintegration_kotlin.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)


        var name: String
        var email: String = ""


        val bundle = intent.extras

        if (bundle != null) {
            name = bundle!!.getString("name").toString()
            email = bundle!!.getString("email").toString()
            var imagurl = bundle!!.getString("imageurl")
            Glide.with(this).load(imagurl).into(binding.imageView)
        } else {
            name = auth.currentUser?.displayName.toString()
//            var imagurl = auth.currentUser?.photoUrl
            Glide.with(this).load(auth.currentUser?.photoUrl).into(binding.imageView)

        }


        binding.name.text = name
        binding.email.text = email



    }
}