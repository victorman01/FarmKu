package com.farmkuindonesia.farmku.ui.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.databinding.ActivityHomeBinding
import com.farmkuindonesia.farmku.ui.banner.BannerActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class HomeActivity : AppCompatActivity() {
    private var _binding: ActivityHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        auth = Firebase.auth
        val firebaseUser = auth.currentUser
        if (firebaseUser == null) {
            startActivity(Intent(this, BannerActivity::class.java))
            finish()
            return
        }

        binding.btnSignOut.setOnClickListener{
            signOut()
        }
    }

    private fun signOut() {
        auth.signOut()
        startActivity(Intent(this, BannerActivity::class.java))
        finish()
    }
}