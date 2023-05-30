package com.farmkuindonesia.farmku.ui.splashscreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.farmkuindonesia.farmku.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = Firebase.auth
    }

    //should be in viewmodel
    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null){
            Handler(Looper.getMainLooper()).postDelayed({
//                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }, 2000L)
        }
        else{
            Handler(Looper.getMainLooper()).postDelayed({
//                val intent = Intent(this, Banner1Activity::class.java)
                startActivity(intent)
                finish()
            }, 2000L)
        }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }
}