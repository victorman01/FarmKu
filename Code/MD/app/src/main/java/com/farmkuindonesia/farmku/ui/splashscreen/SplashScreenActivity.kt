package com.farmkuindonesia.farmku.ui.splashscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.constraintlayout.motion.widget.MotionLayout
import com.farmkuindonesia.farmku.databinding.ActivitySplashScreenBinding
import com.farmkuindonesia.farmku.ui.banner.BannerActivity
import com.farmkuindonesia.farmku.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var motionLayout: MotionLayout
    private var currentUser: FirebaseUser? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        auth = Firebase.auth

        motionLayout = binding.motionLayout

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {}

            override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {}

            override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {}

            override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {}
        })

        Handler(Looper.getMainLooper()).postDelayed({
            motionLayout.transitionToEnd()
        }, 500L)
    }

    private fun updateUI(currentUser: FirebaseUser?) {
        if (currentUser != null) {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 4000L)
        } else {
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(this, BannerActivity::class.java)
                startActivity(intent)
                finish()
            }, 4000L)
        }
    }

    override fun onStart() {
        super.onStart()
        currentUser = auth.currentUser
        updateUI(currentUser)
    }
}

