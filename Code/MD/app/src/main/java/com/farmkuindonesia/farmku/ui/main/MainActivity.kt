package com.farmkuindonesia.farmku.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.databinding.ActivityMainBinding
import com.farmkuindonesia.farmku.ui.onboarding.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
            return
        }

        binding.btnSignOut.setOnClickListener{
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, OnBoardingActivity::class.java))
        finish()
    }
}