package com.farmkuindonesia.farmku.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.farmkuindonesia.farmku.R
import com.farmkuindonesia.farmku.database.Preferences
import com.farmkuindonesia.farmku.databinding.ActivityMainBinding
import com.farmkuindonesia.farmku.ui.ViewModelFactory
import com.farmkuindonesia.farmku.ui.home.HomeFragment
import com.farmkuindonesia.farmku.ui.login.LoginViewModel
import com.farmkuindonesia.farmku.ui.onboarding.OnBoardingActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mainActivityViewModel:MainActivityViewModel
    private lateinit var viewModelFac: ViewModelFactory
    private var loggedInWith: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModelFac = ViewModelFactory.getInstance(this)
        mainActivityViewModel = ViewModelProvider(this, viewModelFac)[MainActivityViewModel::class.java]

        val fragmentManager = supportFragmentManager
        val homeFragment = HomeFragment()
        val fragment = fragmentManager.findFragmentByTag(HomeFragment::class.java.simpleName)

        if (fragment !is HomeFragment) {
            fragmentManager
                .beginTransaction()
                .add(R.id.fragmentContainer, homeFragment, HomeFragment::class.java.simpleName)
                .commit()
        }

        val preferences = getSharedPreferences(Preferences.PREFERENCES, Context.MODE_PRIVATE)
        loggedInWith = preferences.getString(Preferences.LOGGEDINWITH, "NONE")
        if (loggedInWith == "EMAIL") {

        } else if (loggedInWith == "GOOGLE") {
            auth = Firebase.auth
            val firebaseUser = auth.currentUser
            if (firebaseUser == null) {
                startActivity(Intent(this, OnBoardingActivity::class.java))
                finish()
                return
            }
        } else {
            startActivity(Intent(this, OnBoardingActivity::class.java))
            finish()
        }

        binding.btnSignOut.setOnClickListener {
            signOut()
        }
    }


    private fun signOut() {
        if (loggedInWith == "EMAIL") {
            mainActivityViewModel.logout()
        } else if (loggedInWith == "GOOGLE") {
            auth.signOut()
        }
        startActivity(Intent(this, OnBoardingActivity::class.java))
        finish()
    }
}