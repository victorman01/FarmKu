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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.farmkuindonesia.farmku.ui.fragment.profile.ListProfileItemAdapter
import com.farmkuindonesia.farmku.ui.onboarding.OnBoardingActivity

class MainActivity : AppCompatActivity(), ListProfileItemAdapter.MainActivityCallback {
    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var mainActivityViewModel: MainActivityViewModel
    private lateinit var viewModelFac: ViewModelFactory
    private var loggedInWith: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val navController = findNavController(R.id.fragmentContainer)

        val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.navigation_home,
            R.id.navigation_news,
            R.id.navigation_lahan_saya,
            R.id.navigation_profile
        ).build()

        setupActionBarWithNavController(navController, appBarConfiguration)
        binding.navView.setupWithNavController(navController)

        viewModelFac = ViewModelFactory.getInstance(this)
        mainActivityViewModel =
            ViewModelProvider(this, viewModelFac)[MainActivityViewModel::class.java]

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
        mainActivityViewModel.message.observe(this) { event ->
            event.getContentIfNotHandled()?.let { text ->
                showMessage(text)
            }
        }
    }
    private fun showMessage(msg: String) {
        val inflater = layoutInflater
        val layout = inflater.inflate(R.layout.custom_toast, findViewById(R.id.custom_toast_container))
        val textView = layout.findViewById<TextView>(R.id.custom_toast_text)
        textView.text = msg
        val toast = Toast(this@MainActivity)
        toast.view = layout
        toast.duration = Toast.LENGTH_SHORT
        toast.setGravity(Gravity.CENTER, 0, 800)
        toast.show()
    }

    override fun finishMainActivity() {
        finish()
    }
}
